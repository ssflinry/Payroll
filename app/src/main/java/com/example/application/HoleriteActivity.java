package com.example.application;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.adapter.MonthAdapter;
import com.example.application.adapter.YearAdapter;
import com.example.application.model.bean.Data;
import com.example.application.model.bean.Holerite;
import com.example.application.model.dao.DataController;
import com.example.application.model.dao.HoleriteController;
import com.example.application.model.dao.UserController;
import com.example.application.utilities.CalculadoraFolhaPagamento;
import com.example.application.utilities.FormatadorValores;
import com.example.application.utilities.MonthItem;
import com.example.application.utilities.YearItem;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HoleriteActivity extends AppCompatActivity {
    private static final int CREATEPDF = 1;
    private boolean Indexed = false;
    private Button SalvarPdf;
    private ImageView searchButton;
    private ImageView backButton3;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private EditText adiantamentoQuinzenalEditText;
    private EditText salarioLiquidoEditText;
    private EditText totalDescontoEditText;
    private EditText inssEditText;
    private EditText impostoRendaEditText;
    private EditText fgtsEditText;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holerite);

        initializeViews();
        token = getIntent().getStringExtra("token");

        searchButton.setOnClickListener(v -> {
            MonthItem selectedMonth = (MonthItem) monthSpinner.getSelectedItem();
            YearItem selectedYear = (YearItem) yearSpinner.getSelectedItem();
            List<Holerite> list = getHoleriteList(selectedMonth.getMonth(), selectedYear.getYear());

            if (!list.isEmpty()) {
                Holerite holerite = list.get(0);
                fillHoleriteDetails(holerite);
                Indexed = true;
            }
        });

        initializeSpinners();
        backButton3.setOnClickListener(v -> navigateToMenuActivity());

        SalvarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Indexed){
                    MonthItem selectedMonth = (MonthItem) monthSpinner.getSelectedItem();
                    YearItem selectedYear = (YearItem) yearSpinner.getSelectedItem();
                    criarPdf("Holerite-"+selectedMonth+"-"+selectedYear);
                    Indexed = false;
                } else{
                    Toast.makeText(HoleriteActivity.this, "Realize a pesquisa do holerite primeiro, antes de salvá-lo.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void initializeViews() {
        backButton3 = findViewById(R.id.backButton3);
        monthSpinner = findViewById(R.id.spinner);
        yearSpinner = findViewById(R.id.spinner2);
        adiantamentoQuinzenalEditText = findViewById(R.id.AdiantamentoQuinzenalEditText);
        salarioLiquidoEditText = findViewById(R.id.salarioLiquidoEditText);
        totalDescontoEditText = findViewById(R.id.totalDescontoEditText);
        inssEditText = findViewById(R.id.inssEditText);
        impostoRendaEditText = findViewById(R.id.impostoRendaEditText);
        fgtsEditText = findViewById(R.id.fgtsEditText);
        searchButton = findViewById(R.id.searchButton);
        SalvarPdf = findViewById(R.id.salvarPdf);
    }

    private List<Holerite> getHoleriteList(int month, int year) {
        ProgressDialog progressDialog = new ProgressDialog(HoleriteActivity.this);
        progressDialog.setMessage("Puxando dados...");
        progressDialog.show();

        HoleriteController holeriteController = new HoleriteController();
        UserController userController = new UserController();

        new android.os.Handler().postDelayed(() -> {
            List<Holerite> holeriteList = holeriteController.getByMonthAndYear(userController.obterFuncionarioId(token), month, year);

            progressDialog.dismiss();

            if (holeriteList.isEmpty()) {
                runOnUiThread(() -> {
                    Toast toast = Toast.makeText(HoleriteActivity.this, "Não há informações de holerite disponíveis para o mês e ano selecionados.", Toast.LENGTH_LONG);
                    toast.show();
                    new Handler().postDelayed(toast::cancel, 3000);
                });
            } else {
                Holerite holerite = holeriteList.get(0);
                fillHoleriteDetails(holerite);
                Indexed = true;
            }
        }, 1000);

        return new ArrayList<>();
    }

    @SuppressLint("SetTextI18n")
    private void fillHoleriteDetails(Holerite holerite) {
        adiantamentoQuinzenalEditText.setText("Adiantamento Quinzenal: "+FormatadorValores.formatarMoeda(holerite.getAdiantamentoQuinzenal()));
        salarioLiquidoEditText.setText("Salário Liquido: "+FormatadorValores.formatarMoeda(holerite.getSalarioLiquido()));
        totalDescontoEditText.setText("Total Desconto: "+FormatadorValores.formatarMoeda(holerite.getTotalDesconto()));

        double inss = CalculadoraFolhaPagamento.calcularINSS(holerite.getSalarioBruto());
        double impostoRenda = CalculadoraFolhaPagamento.calcularImpostoRenda(holerite.getSalarioBruto());
        double fgts = CalculadoraFolhaPagamento.calcularFGTS(holerite.getSalarioBruto());

        inssEditText.setText("INSS: "+FormatadorValores.formatarMoeda(inss));
        impostoRendaEditText.setText("Imposto de Renda: "+FormatadorValores.formatarMoeda(impostoRenda));
        fgtsEditText.setText("FGTS: "+FormatadorValores.formatarMoeda(fgts));
    }

    private void initializeSpinners() {
        List<MonthItem> monthsList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthsList.add(new MonthItem(i));
        }

        MonthAdapter monthAdapter = new MonthAdapter(this, monthsList);
        monthSpinner.setAdapter(monthAdapter);

        List<YearItem> yearsList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = 2020; year <= currentYear; year++) {
            yearsList.add(new YearItem(year));
        }

        YearAdapter yearAdapter = new YearAdapter(this, yearsList);
        yearSpinner.setAdapter(yearAdapter);
    }

    private void navigateToMenuActivity() {
        Intent intent = new Intent(HoleriteActivity.this, MenuActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }


    public void criarPdf(String title) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        startActivityForResult(intent, CREATEPDF);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATEPDF) {
            if (data.getData() != null) {
                if (!(TextUtils.isEmpty(adiantamentoQuinzenalEditText.getText())) && !(TextUtils.isEmpty(salarioLiquidoEditText.getText())) && !(TextUtils.isEmpty(totalDescontoEditText.getText())) && !(TextUtils.isEmpty(inssEditText.getText())) && !(TextUtils.isEmpty(impostoRendaEditText.getText())) && !(TextUtils.isEmpty(fgtsEditText.getText()))) {
                    Uri caminhDoArquivo = data.getData();
                    MonthItem selectedMonth = (MonthItem) monthSpinner.getSelectedItem();
                    YearItem selectedYear = (YearItem) yearSpinner.getSelectedItem();
                    DataController dataController = new DataController();
                    UserController userController = new UserController();
                    List<Data> lista = dataController.getAll(userController.obterFuncionarioId(token));
                    Data objData = lista.get(0);


                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1240, 1754, 1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                    Canvas canvas = page.getCanvas();
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(50f);
                    paint.setFakeBoldText(true);
                    canvas.drawText("Holerite - "+FormatadorValores.getMesPorExtenso(selectedMonth.getMonth())+" de "+selectedYear, pageInfo.getPageWidth() / 2, 50, paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setTextSize(35f);
                    paint.setFakeBoldText(false);

                    canvas.drawText("Nome Completo: "+ objData.getNomeCompleto(), 50, 100, paint);
                    canvas.drawText("Cargo: " + objData.getCargo(), 50, 130, paint);
                    canvas.drawText("ID do Funcionário: " + objData.getFuncionario_id(), 50, 160, paint);
                    canvas.drawText("RG: " + FormatadorValores.formatarRG(objData.getRg()), 50, 190, paint);
                    canvas.drawText("Data de Admissão: " + FormatadorValores.formatarData(objData.getDataAdmissao()), 50, 220, paint);

                    canvas.drawText(adiantamentoQuinzenalEditText.getText().toString(),50,325 , paint);
                    canvas.drawText(salarioLiquidoEditText.getText().toString(), 50, 355, paint);
                    canvas.drawText(totalDescontoEditText.getText().toString(), 50, 385, paint);

                    canvas.drawText(inssEditText.getText().toString(), 50, 430, paint);
                    canvas.drawText(impostoRendaEditText.getText().toString(), 50, 460, paint);
                    canvas.drawText(fgtsEditText.getText().toString(), 50, 490, paint);


                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(30f);
                    paint.setFakeBoldText(true);

                    canvas.drawText("Data/hora de emissão do holerite: "+FormatadorValores.formatarDataHora(new Date()),440, 580, paint);
                    canvas.drawText("Assinatura do funcionário: ",250, 630, paint);

                    canvas.drawLine(48, 270, pageInfo.getPageWidth() - 100, 280, paint);

                    canvas.drawLine(48, 540, pageInfo.getPageWidth() - 100, 550, paint);

                    pdfDocument.finishPage(page);
                    gravarPdf(caminhDoArquivo, pdfDocument);
                }
            }
        }
    }
    private void gravarPdf(Uri caminhDoArquivo, PdfDocument pdfDocument) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(Objects.requireNonNull(getContentResolver().openOutputStream(caminhDoArquivo)));
            pdfDocument.writeTo(stream);
            pdfDocument.close();
            stream.flush();
            Toast.makeText(this, "Arquivo salvo como .pdf", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Erro de arquivo não encontrado", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Erro de entrada e saída", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro desconhecido" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
