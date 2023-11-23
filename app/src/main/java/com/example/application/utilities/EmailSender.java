package com.example.application.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.application.model.dao.RecoveryController;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String SMTP_USERNAME = "hendrickson.macallister@gmail.com";
    private static final String SMTP_PASSWORD = "aggd qqia rksf xctf";


    public static void sendEmail(final Context context, String to, String subject, String messageBody) {

        new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... strings) {

                try {
                    String to = strings[0];
                    String subject = strings[1];
                    String messageBody = strings[2];

                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", SMTP_HOST);
                    props.put("mail.smtp.port", SMTP_PORT);
                    props.put("mail.smtp.ssl.enable", "true");
                    props.put("mail.smtp.socketFactory.port", SMTP_PORT);
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.socketFactory.fallback", "false");

                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
                        }
                    });

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(SMTP_USERNAME));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    message.setSubject(subject);
                    message.setText(messageBody);

                    Transport.send(message);
                    return true;
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Toast.makeText(context, "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Falha na operação. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(to, subject, messageBody);
    }

    public static boolean sendConfirmationCode(final Context context, String email, String code) {
        RecoveryController recoveryController = new RecoveryController();
        if (recoveryController.isEmailExists(email)) {
            EmailSender.sendEmail(context, email, "Instruções para Redefinição de Senha - Código de Verificação", "Prezado(a) Usuário,\n" +
                    "\n" +
                    "Esperamos que este email o encontre bem. Recebemos uma solicitação para redefinir a senha da sua conta e estamos prontos para ajudar. Para garantir a segurança da sua conta, solicitamos que siga as instruções abaixo para inserir o código de verificação no aplicativo:\n" +
                    "\n" +
                    "Passo 1: Abra o aplicativo no seu dispositivo móvel.\n" +
                    "\n" +
                    "Passo 2: Selecione a opção \"Redefinir Senha\" na tela de login.\n" +
                    "\n" +
                    "Passo 3: Na próxima tela, você será solicitado a inserir o código de verificação de seis dígitos fornecido abaixo:\n" +
                    "\n" +
                    "Código de Verificação: " +code+
                    "\n\n" +
                    "Passo 4: Após inserir o código, siga as instruções na tela para criar uma nova senha segura para a sua conta.\n" +
                    "\n" +
                    "Agradecemos pela sua cooperação e compreensão. Se precisar de mais assistência, não hesite em entrar em contato.");
            return true;
        } else {
            Toast.makeText(context, "Email não encontrado. Verifique o email e tente novamente.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static String generateConfirmationCode() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return code.toString();
    }


}
