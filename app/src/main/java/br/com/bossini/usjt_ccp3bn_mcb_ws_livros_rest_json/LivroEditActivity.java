package br.com.bossini.usjt_ccp3bn_mcb_ws_livros_rest_json;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class LivroEditActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private EditText tituloEditText, autorEditText, editorEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_edit);

        tituloEditText = findViewById(R.id.titulo_message);
        autorEditText = findViewById(R.id.autor_message);
        editorEditText = findViewById(R.id.editor_message);

        requestQueue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.save_livro);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Livro livro = new Livro(tituloEditText.getText().toString(),
                        autorEditText.getText().toString(),
                        editorEditText.getText().toString());
                try {
                    save(livro);
                    finish();
                } catch (JSONException e) {
                    Toast toast = Toast.makeText(view.getContext(), getString(R.string.somethig_got_wrong), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            private void save(Livro livro) throws JSONException {
                sendSaveRequest(jsonObject(livro));
            }

            private void sendSaveRequest(JSONObject jsonObject) {
                String url = getString(R.string.host_address) + getString(R.string.host_port) +
                        getString(R.string.endpoint_base) + getString(R.string.endpoint_salvar);

                JsonObjectRequest req = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("madei pra base");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );
                requestQueue.add(req);
            }

            private JSONObject jsonObject(Livro livro) throws JSONException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("titulo", livro.getTitulo());
                jsonObject.put("autor", livro.getAutor());
                jsonObject.put("edicao", livro.getEdicao());
                return jsonObject;
            }
        });
    }
}
