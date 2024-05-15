/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Jared
 */
public class Atributos {
    
    //INFANTES
    private String nominf, apeinf;
    private int idinf, edad, fk_numtutor_inf;
    
    //TUTORES
    private String nomtutor, apetutor;
    private int numtutor;
    
    //VENTAS
    private String tipo_pago, datetime_venta, tiempo;
    private int folio, fk_idinf_sell;
    private float monto;
    
    //ACTIVOS
    private int idactivo, fk_idinf_act, fk_numtutor_act;    
    
    //HISTORIAL DE ELIMINACIONES
    private int idElim; 
    private String datetime_elim;
    
    //CORTE DE CAJA
    private int idcorte_caja; 
    private String turno, datetime_turno;
    private float monto_total; 
    
    //TURNO USUARIO
    private int idturno; 
    private String usuario, contraseña;
    
        public Atributos(){
    nominf=null; apeinf=null; idinf=0; edad=0; fk_numtutor_inf=0;
    numtutor=0; nomtutor=null; apetutor=null;
    folio=0; fk_idinf_sell=0; monto=0; tipo_pago=null; datetime_venta=null; tiempo=null;
    idactivo=0; fk_idinf_act=0; fk_numtutor_act=0;
    idElim=0; datetime_elim=null;
    idcorte_caja=0; turno=null; datetime_turno=null; monto_total=0;
    idturno=0; usuario=null; contraseña=null;
    
    }
}
