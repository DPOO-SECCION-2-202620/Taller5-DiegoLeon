package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;


public class RestauranteTest
{
	private static final String CARPETA_DATA = "./data/";
    private static final String CARPETA_PRUEBAS = "./data/pruebas/";
    private Restaurante restaurante;
    private File facturaCreada;
    private File archivoPrueba( String nombre )
    {
        return new File( CARPETA_PRUEBAS + nombre );
    }

    @BeforeEach
    void setUp( )
    {
        restaurante = new Restaurante( );
        facturaCreada = null;
    }

    @AfterEach
    void tearDown( )
    {
        if( facturaCreada != null )
            facturaCreada.delete( );
    }

    @Test
    void testRestauranteVacio( )
    {
        assertTrue( restaurante.getPedidos( ).isEmpty( ), "Un restaurante nuevo no debe tener pedidos." );
        assertTrue( restaurante.getIngredientes( ).isEmpty( ), "Un restaurante nuevo no debe tener ingredientes." );
        assertTrue( restaurante.getMenuBase( ).isEmpty( ), "Un restaurante nuevo no debe tener productos en el menú." );
        assertTrue( restaurante.getMenuCombos( ).isEmpty( ), "Un restaurante nuevo no debe tener combos." );
        assertNull( restaurante.getPedidoEnCurso( ), "Un restaurante nuevo no debe tener pedido en curso." );
    }

    @Test
    void testCargarInformacionArchivosReales( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( new File( CARPETA_DATA + "ingredientes.txt" ), new File( CARPETA_DATA + "menu.txt" ), new File( CARPETA_DATA + "combos.txt" ) );

        assertEquals( 15, restaurante.getIngredientes( ).size( ), "No se cargaron todos los ingredientes." );
        assertEquals( 22, restaurante.getMenuBase( ).size( ), "No se cargaron todos los productos del menú." );
        assertEquals( 4, restaurante.getMenuCombos( ).size( ), "No se cargaron todos los combos." );
    }

    @Test
    void testCargarArchivosConLineasVacias( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( archivoPrueba( "ingredientes_validos.txt" ), archivoPrueba( "menu_valido.txt" ), archivoPrueba( "combos_validos.txt" ) );

        assertEquals( 3, restaurante.getIngredientes( ).size( ), "Las líneas vacías no deben generar ingredientes." );
        assertEquals( 3, restaurante.getMenuBase( ).size( ), "Las líneas vacías no deben generar productos." );
        assertEquals( 2, restaurante.getMenuCombos( ).size( ), "Las líneas vacías no deben generar combos." );
    }


    @Test
    void testIngredienteRepetido( )
    {
        assertThrows( IngredienteRepetidoException.class,
                ( ) -> restaurante.cargarInformacionRestaurante( archivoPrueba( "ingredientes_repetidos.txt" ), archivoPrueba( "menu_valido.txt" ), archivoPrueba( "combos_validos.txt" ) ),
                "Se esperaba IngredienteRepetidoException." );
    }

    @Test
    void testProductoRepetido( )
    {
        assertThrows( ProductoRepetidoException.class,
                ( ) -> restaurante.cargarInformacionRestaurante( archivoPrueba( "ingredientes_validos.txt" ), archivoPrueba( "menu_repetido.txt" ), archivoPrueba( "combos_validos.txt" ) ),
                "Se esperaba ProductoRepetidoException." );
    }

    @Test
    void testComboRepetido( )
    {
        assertThrows( ProductoRepetidoException.class,
                ( ) -> restaurante.cargarInformacionRestaurante( archivoPrueba( "ingredientes_validos.txt" ), archivoPrueba( "menu_valido.txt" ), archivoPrueba( "combos_repetidos.txt" ) ),
                "Se esperaba ProductoRepetidoException para un combo repetido." );
    }

    @Test
    void testComboConProductoFaltante( )
    {
        assertThrows( ProductoFaltanteException.class,
                ( ) -> restaurante.cargarInformacionRestaurante( archivoPrueba( "ingredientes_validos.txt" ), archivoPrueba( "menu_valido.txt" ), archivoPrueba( "combos_producto_faltante.txt" ) ),
                "Se esperaba ProductoFaltanteException." );
    }


    @Test
    void testIniciarPedido( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 19" );

        assertNotNull( restaurante.getPedidoEnCurso( ), "Después de iniciar un pedido debe haber un pedido en curso." );
        assertEquals( "Juan", restaurante.getPedidoEnCurso( ).getNombreCliente( ), "El pedido en curso no es del cliente esperado." );
    }

    @Test
    void testIniciarPedidoConPedidoEnCurso( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 19" );
        assertThrows( YaHayUnPedidoEnCursoException.class, ( ) -> restaurante.iniciarPedido( "Ana", "Carrera 7" ), "Se esperaba YaHayUnPedidoEnCursoException." );
    }

    @Test
    void testCerrarSinPedidoEnCurso( )
    {
        assertThrows( NoHayPedidoEnCursoException.class, ( ) -> restaurante.cerrarYGuardarPedido( ), "Se esperaba NoHayPedidoEnCursoException." );
    }

    @Test
    void testCerrarYGuardarPedido( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan", "Calle 19" );
        Pedido pedido = restaurante.getPedidoEnCurso( );

        restaurante.cerrarYGuardarPedido( );
        facturaCreada = new File( "./facturas/factura_" + pedido.getIdPedido( ) + ".txt" );

        assertTrue( facturaCreada.exists( ), "Se debió crear el archivo " + facturaCreada.getPath( ) );
        assertNull( restaurante.getPedidoEnCurso( ), "Después de cerrar el pedido no debe haber pedido en curso." );
        assertEquals( 1, restaurante.getPedidos( ).size( ), "El pedido cerrado debe quedar en la lista de pedidos." );
    }
    
}
