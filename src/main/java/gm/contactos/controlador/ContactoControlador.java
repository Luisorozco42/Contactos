package gm.contactos.controlador;

import gm.contactos.modelo.Contacto;
import gm.contactos.servicio.ContactoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContactoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ContactoControlador.class);

    @Autowired
    ContactoServicio contactoServicio;

    @GetMapping("/")
    public String iniciar(ModelMap model) {
        List<Contacto> contactos = contactoServicio.listarContactos();
        contactos.forEach(contacto -> logger.info(contactos.toString()));
        model.put("contactos", contactos);
        return  "index"; //index.html
    }

    @GetMapping("/agregar")
    public String mostarAgregar(ModelMap model) {
        return "agregar";
    }

    @PostMapping("/agregar")
    public String agregar (@ModelAttribute("contactoForma") Contacto contacto) {
        logger.info("Contacto a agregar: " + contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/"; //redirigimos al path de inicio
    }

    @GetMapping("/editar/{id}")
    public String mostarEditar(@PathVariable(value = "id") int idContacto, ModelMap model) {
        Contacto contacto = contactoServicio.buscarContactoPorId(idContacto);
        model.put("contacto", contacto);
        return "editar";
    }

    @PostMapping("/editar")
    public String editar (@ModelAttribute("contacto") Contacto contacto) {
        logger.info("Contacto a editar: " + contacto);
        contactoServicio.guardarContacto(contacto);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") int idContacto, ModelMap model) {
        Contacto contacto = new Contacto();
        contacto.setIdContacto(idContacto);
        contactoServicio.eliminarContacto(contacto);
        return "redirect:/";
    }
}
