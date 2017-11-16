package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 10/08/2017.
 */

public class fragmentOCR extends Fragment {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    StringBuilder stringBuilder = new StringBuilder();
    String detectado;
    Map<String, String> varsOCR = new HashMap<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ocr, container, false);

        cameraView = (SurfaceView) view.findViewById(R.id.surface_view);
        textView = (TextView) view.findViewById(R.id.text_view);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("OCR", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getActivity(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < items.size(); ++i) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                //textView.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }

        Button button = (Button) view.findViewById(R.id.stopOCR);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectado = stringBuilder.toString();
                stop();

                buscar(detectado);

                MainActivity myParent = (MainActivity)getActivity();
                myParent.setVarsOCR(varsOCR);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentLaboratorio(), "laboratorioTag")
                        .addToBackStack("laboratorioTag").commit();
            }
        });

        return view;
    }

    public void buscar(String texto)
    {
        String detectadomin = texto.toLowerCase().trim();
        detectadomin = detectadomin.replaceAll(" ","");
        detectadomin = detectadomin.replaceAll("\\,",".");
        detectadomin = detectadomin.replaceAll("[\"()*&^$+#=@!'-]","");
        String aux = "";
        for (int i = 0; i < 5; i++) {
            switch (i){
                case 0:
                    aux = encontrar("ph:[0-9]{1,3}(.){0,1}[0-9]{0,2}",detectadomin);
                break;
                case 1:
                    aux = encontrar("hco3:[0-9]{1,3}(.){0,1}[0-9]{0,2}",detectadomin);
                    break;
                case 2:
                    aux = encontrar("cl:[0-9]{1,3}(.){0,1}[0-9]{0,2}",detectadomin);
                    break;
                case 3:
                    aux = encontrar("pco2:[0-9]{1,3}(.){0,1}[0-9]{0,2}",detectadomin);
                    break;
                case 4:
                    aux = encontrar("na:[0-9]{1,3}(.){0,1}[0-9]{0,2}",detectadomin);
                    break;
            }
            if (aux.length() > 0){
                String[] partes = aux.split(":");
                varsOCR.put(partes[0], partes[1]);
            }
        }
    }
    public String encontrar(String regex, String TextoaUsar)
    {
        Pattern checkRegex = Pattern.compile(regex);
        Matcher regexMatcher = checkRegex.matcher(TextoaUsar);
        String returnable = "";

        while (regexMatcher.find())
            if (regexMatcher.group().length() != 0)
                returnable = regexMatcher.group().trim().toString();

        return returnable;
    }

    public void stop()
    {
        cameraSource.stop();
    }
}
