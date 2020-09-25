package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DataBase dataBase;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = new DataBase(this);
        listView = findViewById(R.id.listView);

        loadAllTask();
    }

    private void loadAllTask() {
        ArrayList<String> arrayList = dataBase.getAllTask();
        listView.setAdapter(arrayAdapter);
        if (arrayAdapter == null){
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.task_list_row, R.id.text_label, arrayList);
            listView.setAdapter(arrayAdapter);
        } else {
            arrayAdapter.clear();
            arrayAdapter.addAll(arrayList);
            arrayAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {

        if (item.getItemId() == R.id.add_new_task){
            final EditText editText = new EditText(this);
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Добавление нового задания")
                    .setMessage("Чтобы вы хотели добавить?")
                    .setView(editText)
                    .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String task = String.valueOf(editText.getText());
                            dataBase.insertData(task);
                            loadAllTask();
                        }
                    })
                    .setNegativeButton("Отмена", null)
                    .create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View button){
        View parent = (View)button.getParent();
        TextView textView = parent.findViewById(R.id.text_label);
        String text = String.valueOf(textView.getText());
        dataBase.deleteData(text);
        loadAllTask();
    }


}