package co.com.ceiba.parqueaderofirebase.data.entities;

public class    Parqueadero {

    private int adicionCilindraje;
    private int cantidadCarros;
    private int cantidadMotos;
    private long valorDiaCarro;
    private long valorDiaMoto;
    private long valorHoraCarro;
    private long valorHoraMoto;
    private int topeCilindraje;

    public Parqueadero() {
    }

    public Parqueadero(int adicionCilindraje, int cantidadCarros, int cantidadMotos, long valorDiaCarro, long valorDiaMoto, long valorHoraCarro, long valorHoraMoto, int topeCilindraje) {
        this.adicionCilindraje = adicionCilindraje;
        this.cantidadCarros = cantidadCarros;
        this.cantidadMotos = cantidadMotos;
        this.valorDiaCarro = valorDiaCarro;
        this.valorDiaMoto = valorDiaMoto;
        this.valorHoraCarro = valorHoraCarro;
        this.valorHoraMoto = valorHoraMoto;
        this.topeCilindraje = topeCilindraje;
    }

    public int getAdicionCilindraje() {
        return adicionCilindraje;
    }

    public void setAdicionCilindraje(int adicionCilindraje) {
        this.adicionCilindraje = adicionCilindraje;
    }

    public int getCantidadCarros() {
        return cantidadCarros;
    }

    public void setCantidadCarros(int cantidadCarros) {
        this.cantidadCarros = cantidadCarros;
    }

    public int getCantidadMotos() {
        return cantidadMotos;
    }

    public void setCantidadMotos(int cantidadMotos) {
        this.cantidadMotos = cantidadMotos;
    }

    public long getValorDiaCarro() {
        return valorDiaCarro;
    }

    public void setValorDiaCarro(long valorDiaCarro) {
        this.valorDiaCarro = valorDiaCarro;
    }

    public long getValorDiaMoto() {
        return valorDiaMoto;
    }

    public void setValorDiaMoto(long valorDiaMoto) {
        this.valorDiaMoto = valorDiaMoto;
    }

    public long getValorHoraCarro() {
        return valorHoraCarro;
    }

    public void setValorHoraCarro(long valorHoraCarro) {
        this.valorHoraCarro = valorHoraCarro;
    }

    public long getValorHoraMoto() {
        return valorHoraMoto;
    }

    public void setValorHoraMoto(long valorHoraMoto) {
        this.valorHoraMoto = valorHoraMoto;
    }

    public int getTopeCilindraje() {
        return topeCilindraje;
    }

    public void setTopeCilindraje(int topeCilindraje) {
        this.topeCilindraje = topeCilindraje;
    }
}
