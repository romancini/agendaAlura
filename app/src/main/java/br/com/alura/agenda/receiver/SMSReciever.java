package br.com.alura.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

public class SMSReciever extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage smsMessage = SmsMessage.createFromPdu(pdu, formato);
        String telefone = smsMessage.getDisplayOriginatingAddress();
        AlunoDAO alunoDAO = new AlunoDAO(context);
        if (alunoDAO.isAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS de Aluno!", Toast.LENGTH_LONG).show();
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.msg);
        }
        alunoDAO.close();
    }
}
