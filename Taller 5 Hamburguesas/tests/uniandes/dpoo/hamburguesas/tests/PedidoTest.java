package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest
{
	private ProductoMenu corral;
    private ProductoMenu gaseosa;
    private ProductoMenu papasMedianas;
    private File archivoFactura;

    private Pedido pedido;
    @BeforeEach
    void setUp( ) throws Exception
    {
    	corral = new ProductoMenu( "corral", 14000 );
        gaseosa = new ProductoMenu( "gaseosa", 5000 );
        papasMedianas = new ProductoMenu( "papas medianas", 5500 );
        pedido = new Pedido( "Juan", "Calle 19 # 1-10" );
        pedido.agregarProducto( corral );
        pedido.agregarProducto( gaseosa );
        archivoFactura = new File( "./data/pruebas/factura_prueba.txt" );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    	archivoFactura.delete( );
    }

    @Test
    void testGetNombreCliente( )
    {
        assertEquals( "Juan", pedido.getNombreCliente( ), "El nombre del cliente no es el esperado." );
    }

    @Test
    void testIdConsecutivo( )
    {
    	 Pedido p1 = new Pedido( "Cliente 1", "Dir 1" );
         Pedido p2 = new Pedido( "Cliente 2", "Dir 2" );
         assertEquals( p1.getIdPedido( ) + 1, p2.getIdPedido( ), "Los identificadores de pedidos deben ser consecutivos." );
    }
    
    @Test
    void testPrecioTotalPedido( )
    {
    	assertEquals( 22610, pedido.getPrecioTotalPedido( ), "El precio total del pedido debe incluir el IVA." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
    	String esperado = "Cliente: Juan\n" + "Dirección: Calle 19 # 1-10\n" + "----------------\n" + "corral\n" + "            14000\n" + "gaseosa\n" + "            5000\n" + "----------------\n"
                + "Precio Neto:  19000\n" + "IVA:          3610\n" + "Precio Total: 22610\n";
        assertEquals( esperado, pedido.generarTextoFactura( ), "La factura del pedido no es la esperada." );
    }
    
    @Test
    void testGuardarFactura( ) throws Exception
    {
    	pedido.guardarFactura( archivoFactura );
        assertTrue( archivoFactura.exists( ), "El archivo de la factura debe existir después de guardarla." );
        String contenido = new String( Files.readAllBytes( archivoFactura.toPath( ) ) );
        assertEquals( pedido.generarTextoFactura( ), contenido, "El archivo debe contener exactamente el texto de la factura." );
    }
}

