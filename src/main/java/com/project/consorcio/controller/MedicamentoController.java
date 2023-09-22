package com.project.consorcio.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Medicamento;
import com.project.consorcio.entity.TipoMedicamento;
import com.project.consorcio.services.MedicamentoServices;
import com.project.consorcio.services.TipoMedicamentoServices;

//Controller, permite que la vista envie un Request y pueda acceder
//a las direntes direcciones URL's
@Controller
//RequestMapping, permite crear direcciones URL para que el cliente acceda
@RequestMapping("/medicamento")
public class MedicamentoController {
	@Autowired
	private MedicamentoServices servicioMed;
	
	@Autowired
	private TipoMedicamentoServices servicioTipo;
	
	
	//crear dirección URL para mostrar la página "medicamento.html"
	@RequestMapping("/lista")
	public String index(Model model){
		//crear atributo
		model.addAttribute("medicamentos",servicioMed.listarTodos());
		model.addAttribute("tipos",servicioTipo.listarTodos());
		return "medicamento";
	}
	//crear dirección URL para registrar Medicamento
	@RequestMapping("/grabar")
	public String grabar(@RequestParam("codigo") Integer codi,
						 @RequestParam("nombre") String nom,
						 @RequestParam("descripcion") String des,
						 @RequestParam("stock") int stock,
						 @RequestParam("precio") double pre,
						 @RequestParam("fecha") String fecha,
						 @RequestParam("tipo") Integer cod,
						 RedirectAttributes redirect) {	
		try {
			//crear objeto de la entidad Medicamento
			Medicamento med=new Medicamento();
			//setear atributos del objeto "med" usando los parámetros
			med.setNombre(nom);
			med.setDescripcion(des);
			med.setStock(stock);
			med.setPrecio(pre);
			med.setFecha(LocalDate.parse(fecha));
			//crear objeto de le entidad TipoMedicamento
			TipoMedicamento tp=new TipoMedicamento();
			//setear atributo codigo
			tp.setCodigo(cod);
			//enviar objeto "tp" al objeto "med"
			med.setTipo(tp);
			//validar codi
			if(codi==0) {
				//invocar al método registrar
				servicioMed.registrar(med);
				//mensaje +
				redirect.addFlashAttribute("MENSAJE","Medicamento registrado");
			}
			else {
				//setear atributo codigo
				med.setCodigo(codi);
				servicioMed.actualizar(med);
				//mensaje +
				redirect.addFlashAttribute("MENSAJE","Medicamento actualizado");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/medicamento/lista";
	}
	//crear dirección URL para buscar Medicamento por código
	@RequestMapping("/consultaPorID")
	@ResponseBody
	public Medicamento consultaPorID(@RequestParam("codigo") Integer cod){
		return servicioMed.buscarPorID(cod);
	}
	
	
	
	
	
}






