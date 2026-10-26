package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
	private Ingrediente queso;
    private Ingrediente cebolla;
    private ProductoAjustado ajustado;
	

    @BeforeEach
    void setUp( ) throws Exception
    {
    	ProductoMenu corral = new ProductoMenu( "corral", 14000 );
        queso = new Ingrediente( "queso mozzarella", 2500 );
        cebolla = new Ingrediente( "cebolla", 1000 );
        ajustado = new ProductoAjustado( corral );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", ajustado.getNombre( ), "El nombre del producto ajustado debe ser el del producto base." );
    }

    @Test
    void testGetPrecioSinModificaciones( )
    {
        assertEquals( 14000, ajustado.getPrecio( ), "Sin ajustes, el precio debe ser igual al del producto base." );
    }
    @Test
    void testGetPrecioConAgregado( )
    {
        ajustado.agregarIngrediente( queso );
        assertEquals( 16500, ajustado.getPrecio( ), "El precio debe sumar el costo del ingrediente agregado." );
    }

    @Test
    void testGetPrecioEliminarNoDescuenta( )
    {
        ajustado.eliminarIngrediente( cebolla );
        assertEquals( 14000, ajustado.getPrecio( ), "Eliminar un ingrediente no debe reducir el precio." );
    }
    
    @Test
    void testGenerarTextoFacturaConAjustes( )
    {
        ajustado.agregarIngrediente( queso );
        ajustado.eliminarIngrediente( cebolla );

        String esperado = "corral\n" + "    +queso mozzarella                2500\n" + "    -cebolla\n" + "            16500\n";
        assertEquals( esperado, ajustado.generarTextoFactura( ), "El texto de la factura del producto ajustado no es el esperado." );
    }

}
