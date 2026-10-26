package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
	private ProductoMenu corral;
    private ProductoMenu papasMedianas;
    private ProductoMenu gaseosa;
    private ArrayList<ProductoMenu> items;
    private Combo comboCorral;
	

    @BeforeEach
    void setUp( ) throws Exception
    {
        corral = new ProductoMenu( "corral", 14000 );
        papasMedianas = new ProductoMenu( "papas medianas", 5500 );
        gaseosa = new ProductoMenu( "corral", 5000 );
        
        items = new ArrayList<ProductoMenu>( );
        items.add(corral);
        items.add(papasMedianas);
        items.add(gaseosa);
        
        comboCorral = new Combo("combo corral",0.1,items);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "combo corral", comboCorral.getNombre( ), "El nombre del combo no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
    	assertEquals( 22050, comboCorral.getPrecio( ), "El precio del combo debe tener aplicado el descuento." );
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
    	String esperado = "Combo combo corral\n" + " Descuento: 0.1\n"+"            22050\n";
        assertEquals( esperado, corral.generarTextoFactura( ) );
    }

}

