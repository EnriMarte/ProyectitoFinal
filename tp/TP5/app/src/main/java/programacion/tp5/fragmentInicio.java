package programacion.tp5;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by PC on 10/6/2017.
 */

public class fragmentInicio extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio, container, false);

        Button button = (Button)view.findViewById(R.id.btnEntrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new fragmentLogin(),
                        "loginTag").commit();
            }
        });

        return view;
    }
}
