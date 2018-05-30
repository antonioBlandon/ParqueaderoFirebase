package co.com.ceiba.parqueaderofirebase;

import co.com.ceiba.parqueaderofirebase.data.entities.Parqueadero;

public class ParqueaderoBuilder {

    private int adicionCilindraje;
    private int cantidadCarros;
    private int cantidadMotos;
    private long valorDiaCarro;
    private long valorDiaMoto;
    private long valorHoraCarro;
    private long valorHoraMoto;
    private int topeCilindraje;

    public ParqueaderoBuilder() {
        this.adicionCilindraje = 2000;
        this.cantidadCarros = 20;
        this.cantidadMotos = 10;
        this.valorDiaCarro = 8000;
        this.valorDiaMoto = 4000;
        this.valorHoraCarro = 1000;
        this.valorHoraMoto = 500;
        this.topeCilindraje = 500;
    }

    public Parqueadero build(){
        return new Parqueadero(adicionCilindraje, cantidadCarros, cantidadMotos, valorDiaCarro, valorDiaMoto, valorHoraCarro, valorHoraMoto, topeCilindraje);
    }

    public static ParqueaderoBuilder aParking(){
        return new ParqueaderoBuilder();
    }

}
