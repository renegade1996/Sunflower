package reg.sunflower;

public class Inicio
{

	public static void main(String[] args)
	{
		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		
		new Controlador(modelo, vista);
	}
}
