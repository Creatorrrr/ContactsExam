package com.example.kosta.contactsexam;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text = (TextView)findViewById(R.id.data);
        text.setText(getContacts());
    }

    private String getContacts() {

        StringBuffer sb = new StringBuffer();

        Cursor contactsCursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        while(contactsCursor.moveToNext()) {
//            저장된 이름 구하기
            String displayName = contactsCursor.getString(
                    contactsCursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
            sb.append("DP Name : " + displayName + "\n");

//            주소록 ID 구하기
            String contactId = contactsCursor.getString(
                    contactsCursor.getColumnIndex(
                            ContactsContract.Contacts._ID));

//            전화번호가 존재하는지 확인
//            저장되어 있으면 1, 없으면 0
            String hasPhoneNumber = contactsCursor.getString(
                    contactsCursor.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER));
            sb.append("hasPhoneNumber : " + hasPhoneNumber + "\n");

//            전화번호가 저장되어 있다면
                if(hasPhoneNumber.equals("1")) {
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                            null, null);

//                    전화번호 모두 검색
                    while(phones.moveToNext()) {
                        String phoneNumber = phones.getString(
                                phones.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sb.append("전화번호 : " + phoneNumber + "\n");
                    }

                    phones.close();
                }
        }

        return sb.toString();
    }
}
