package com.example.diego.listatareas.actividades;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.diego.listatareas.Constantes;
import com.example.diego.listatareas.R;
import com.example.diego.listatareas.fragments.ConfirmDialogFragment;
import com.example.diego.listatareas.fragments.DatePickerFragment;
import com.example.diego.listatareas.fragments.UpdateFragment;

/**
 * Created by diego on 10.12.16.
 */

public class UpdateActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener,
        ConfirmDialogFragment.ConfirmDialogListener {



    /**
     * Etiqueta del valor extra del dialogo
     */
    private static final String EXTRA_NOMBRE = "NOMBRE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String extra = getIntent().getStringExtra(Constantes.EXTRA_ID);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, UpdateFragment.createInstance(extra), "UpdateFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        UpdateFragment updateFragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");

        if (updateFragment != null) {
            updateFragment.actualizarFecha(year, month, day);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        UpdateFragment fragment = (UpdateFragment)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");

        if (fragment != null) {
            String extra = dialog.getArguments().getString(EXTRA_NOMBRE);
            String msg = getResources().
                    getString(R.string.dialog_delete_msg);

            if (extra.compareTo(msg) == 0) {
                fragment.eliminarMeta(); // Eliminar la tarea
            } else {
                finish();
            }

        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }
}

