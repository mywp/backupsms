package com.example.scorpio.backupsms;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.scorpio.backupsms.entities.SmsInfo;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    /*备份短信*/
    public void backupSms(View v){
        //1.查出所有的短信
        Uri uri =Uri.parse("content://sms/");

        ContentResolver resolver =getContentResolver();
        Cursor cursor = resolver.query(uri,new String[]{"_id","address","date","type","body"},null,null,null);
    
        if (cursor !=null && cursor.getCount() >0){
            List<SmsInfo> smsList = new ArrayList<SmsInfo>();
            SmsInfo sms;
            
            while(cursor.moveToNext()){//控制游标结果集的指针向下移一位，当道最后一位，停止，返回false

                sms=new SmsInfo();
                sms.setId(cursor.getInt(0));//设置短信的id
                sms.setAddress(cursor.getString(1));//设置短信的号码
                sms.setDate(cursor.getLong(2));//设置短信的日期
                sms.setType(cursor.getInt(3));//设置短信的类型，接收1还是发送2
                sms.setBody(cursor.getString(4));//设置短信的内容
                smsList.add(sms);
            }
            cursor.close();
            
            //2.序列化到本地
            writeToLocal(smsList);
        }
    }

    /*序列化到本地*/
    private void writeToLocal(List<SmsInfo> smsList) {

        try {
            XmlSerializer serializer = Xml.newSerializer();//得到序列化对象
            //指定输出位置
            FileOutputStream fos =new FileOutputStream("/mnt/sdcard/sms.xml");
            serializer.setOutput(fos, "utf-8");
            
            serializer.startDocument("utf-8", true);
            
            serializer.startTag(null, "smss");
            
            for (SmsInfo smsInfo : smsList){
                serializer.startTag(null, "sms");
                serializer.attribute(null, "id", String.valueOf(smsInfo.getId()));
                
                //写号码
                serializer.startTag(null,"address");
                serializer.text(smsInfo.getAddress());
                serializer.endTag(null, "address");

                //写时间
                serializer.startTag(null,"date");
                serializer.text(String.valueOf(smsInfo.getDate()));
                serializer.endTag(null,"date");

                //写类型
                serializer.startTag(null,"type");
                serializer.text(String.valueOf(smsInfo.getType()));
                serializer.endTag(null,"type");

                //写内容
                serializer.startTag(null,"body");
                serializer.text(String.valueOf(smsInfo.getBody()));
                serializer.endTag(null,"body");
                
                serializer.endTag(null,"sms");
                
            }
            
            serializer.endTag(null,"smss");
            
            serializer.endDocument();

            Toast.makeText(this,"备份成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"备份失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
