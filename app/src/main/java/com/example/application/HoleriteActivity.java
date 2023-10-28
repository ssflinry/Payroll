package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.adapter.MonthAdapter;
import com.example.application.adapter.YearAdapter;
import com.example.application.model.bean.Holerite;
import com.example.application.model.dao.HoleriteDAO;
import com.example.application.model.dao.UserDAO;
import com.example.application.utilities.CalculadoraFolhaPagamento;
import com.example.application.utilities.FormatadorValores;
import com.example.application.utilities.MonthItem;
import com.example.application.utilities.YearItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HoleriteActivity extends AppCompatActivity {

    private ImageView searchButton;
    private ImageView backButton3;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private EditText AdiantamentoQuinzenalEditText;
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
            }
        });

        initializeSpinners();
        backButton3.setOnClickListener(v -> navigateToMenuActivity());
    }

    private void initializeViews() {
        backButton3 = findViewById(R.id.backButton3);
        monthSpinner = findViewById(R.id.spinner);
        yearSpinner = findViewById(R.id.spinner2);
        AdiantamentoQuinzenalEditText = findViewById(R.id.AdiantamentoQuinzenalEditText);
        salarioLiquidoEditText = findViewById(R.id.salarioLiquidoEditText);
        totalDescontoEditText = findViewById(R.id.totalDescontoEditText);
        inssEditText = findViewById(R.id.inssEditText);
        impostoRendaEditText = findViewById(R.id.impostoRendaEditText);
        fgtsEditText = findViewById(R.id.fgtsEditText);
        searchButton = findViewById(R.id.searchButton);
    }

    private List<Holerite> getHoleriteList(int month, int year) {
        HoleriteDAO holeriteDAO = new HoleriteDAO();
        UserDAO userDAO = new UserDAO();
        return holeriteDAO.getByMonthAndYear(userDAO.obterFuncionarioId(token), month, year);
    }

    private void fillHoleriteDetails(Holerite holerite) {
        AdiantamentoQuinzenalEditText.setText(FormatadorValores.formatarMoeda(holerite.getAdiantamentoQuinzenal()));
        salarioLiquidoEditText.setText(FormatadorValores.formatarMoeda(holerite.getSalarioLiquido()));
        totalDescontoEditText.setText(FormatadorValores.formatarMoeda(holerite.getTotalDesconto()));

        double inss = CalculadoraFolhaPagamento.calcularINSS(holerite.getSalarioBruto());
        double impostoRenda = CalculadoraFolhaPagamento.calcularImpostoRenda(holerite.getSalarioBruto());
        double fgts = CalculadoraFolhaPagamento.calcularFGTS(holerite.getSalarioBruto());

        inssEditText.setText(FormatadorValores.formatarMoeda(inss));
        impostoRendaEditText.setText(FormatadorValores.formatarMoeda(impostoRenda));
        fgtsEditText.setText(FormatadorValores.formatarMoeda(fgts));
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
}
