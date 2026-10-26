package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest
{
    private ProductoMenu corral;

    @BeforeEach
    void setUp( ) throws Exception
    {
        corral = new ProductoMenu( "corral", 14000 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", corral.getNombre( ), "El nombre del producto no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
        assertEquals( 14000, corral.getPrecio( ), "El precio del producto no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
    	String esperado = "corral\n" + "            14000\n";
        assertEquals( esperado, corral.generarTextoFactura( ) );
    }

}
