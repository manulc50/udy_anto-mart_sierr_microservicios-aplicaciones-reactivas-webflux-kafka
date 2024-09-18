package controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

import lombok.AllArgsConstructor;
import servicio.ElementoServicio;

@AllArgsConstructor
@Controller
public class ElementoControlador {
	private final ElementoServicio elementoServicio;
	
	// Nota: En vez de tener este método handler que redirige a la página de inicio, otra alternativa válida sería omitir o eliminar
	// este método handler y crear la vista "index.html" en vez de "inicio.html".
	@GetMapping
	public String inicio() {
		return "inicio";
	}
	
	@GetMapping("/elementos")
	public String obtenerElementoPorPrecioMax(@RequestParam double precioMaximo, Model modelo) {
		IReactiveDataDriverContextVariable elementos = 
				new ReactiveDataDriverContextVariable(elementoServicio.obtenerElementosPorPrecioMax(precioMaximo), 1);
		
		// Si pasamos directamente el flujo reactivo de elementos, el cliente tiene que esperar a que se emita el último elemento
		// para mostrarlos en la vista, es decir, en este caso, los elementos se van agrupando en una lista a medida que se emiten
		// y, cuando se agrupa el último elemento, entonces se emite esa lista al cliente(En realidad, internamente se realiza la
		// operación Flux.collectList()).
		//modelo.addAttribute("elementos", elementoServicio.obtenerElementosPorPrecioMax(precioMaximo));
		
		// Sin embargo, si envolvemos el flujo reactivo de elemento dentro de un "ReactiveDataDriverContextVariable", el cliente va
		// mostrando los elementos en la vista a medida que se van emitiendo, es decir, en este caso, los elementos se envían como un
		// stream de eventos(SSE).
		modelo.addAttribute("elementos", elementos);
		
		return "listado";
	}
}
