package afinal.proyecto.proyectofinaldemojunio.Model;

/**
 * Created by ianfr on 16/06/2017.
 */


/*
* valores normales
* ph 7,35 - 7,42 +- 0.03
* pco2 40mmHg +-2
* co3h 24mEq/l +- 2
* sodio 142mEq/l +-3
* cl 103mEq/l +-2
*
*
*
* */
public class Diagnostico {
    public String getDiagnostico(double ph,
                                 double hco3,
                                 double pco2,
                                 double na,
                                 double cl,
                                 double algumina,
                                 boolean agudo){
        String r = descompensacion(ph, hco3, pco2);

        if (r != null) {
            switch (descompensacion(ph, hco3, pco2)) {
                case "acidosis metabolica":
                    break;
                case "acidosis respiratoria":
                    break;
                case "alcalosis metabolica":
                    break;
                case "alcalosis respiratoria":
                    break;
            }
        }

        return r;
    }

    private String descompensacion(double ph,
                                    double hco3,
                                    double pco2){
        if (ph < 7.35){
            //Acidosis
            if (hco3 < 22) {
                //metabolica
                return "acidosis metabolica";
            } else if (pco2 > 42) {
                //respiratoria
                return "acidosis respiratoria";
            }
        } else if (ph > 7.35) {
            //Alcalosis
            if (hco3 > 26) {
                //metabolica
                return "alcalosis metabolica";
            } else if (pco2 < 38) {
                //respiratoria
                return "alcalosis respiratoria";
            }
        }

        return null;
    }

    /*
    * ve = vp * indice
    * vp = valor normal - valor medido
    *
    * trastorno        indice        limite
    * acid. metab.      1.2          10mmHg
    * alcal. metab.     0.7          55mmHg
    * acid. resp. ag.   0.1          30mEq/l
    * alcal. resp. ag.  0.2          16-18mEq/l
    * acid. resp. cr.   0.35         45mEq/l
    * alcal. resp cr.   0.5          12-15mEq/l
    *
    * */
    private String isCompensado(String descompensación,
                                 double anionGap,
                                 double pco2,
                                 double hco3,
                                 boolean agudo) {
        double vp;
        double ve;
        if (descompensación.contains("metabolica")){
            //usa pco2
            vp = 40.00 - pco2;
            if (descompensación.contains("acidosis")) {
                ve = vp * 1.2;
            } else if (descompensación.contains("alcalosis")) {
                ve = vp * 0.7;
            }
        } else if (descompensación.contains("respiratoria")) {
            //usa hco3
            vp = 24.00 - hco3;
            if (agudo) {
                if (descompensación.contains("acidosis")) {
                    ve = vp * 0.1;
                } else if (descompensación.contains("alcalosis")) {
                    ve = vp * 0.2;
                }
            } else {
                if (descompensación.contains("acidosis")) {
                    ve = vp * 0.35;
                } else if (descompensación.contains("alcalosis")) {
                    ve = vp * 0.5;
                }
            }
        }

        return null;
    }

    /* si el ph da entre 7.2 - 7.3 al anion gap se resta en 1
            *           ''      7.1 - 7.2                 resta en 2
            *           ''      < 7.1                     resta en 3
            *           ''      7.5 - 7.6                 suma en 3
            *           ''      7.6 - 7.7                 suma en 4
            *           ''      7.7 - 7.8                 suma en 5
            *           ''      > 7.8                     muerto / error*/
    private double getAnionGap(double ph,
                               double na,
                               double cl,
                               double hco3,
                               double albumina){
        double r = na - (cl + hco3);

        if (ph < 7.1 || ph > 7.8) {
            return -13.37;
        } else if (ph > 7.2 && ph < 7.3) {
            r-=1;
        } else if(ph > 7.1 && ph < 7.2) {
            r-=2;
        } else if(ph < 7.1) {
            r-=3;
        } else if(ph > 7.5 && ph < 7.6) {
            r+=3;
        } else if(ph > 7.6 && ph < 7.7) {
            r+=4;
        } else if(ph > 7.7 && ph < 7.8) {
            r+=5;
        } else if (albumina > 5.4) {
            r+=2;
        } else if (albumina < 3.4) {
            r-=2;
        }

        return r;
    }
}
