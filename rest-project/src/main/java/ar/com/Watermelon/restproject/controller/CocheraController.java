package ar.com.Watermelon.restproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.Watermelon.restproject.dao.CocheraDao;
import ar.com.Watermelon.restproject.model.Cliente;
import ar.com.Watermelon.restproject.model.Cochera;
import ar.com.Watermelon.restproject.model.Vehiculo;

@Controller
@RequestMapping("/cochera")
public class CocheraController extends BaseController<CocheraDao, Cochera>{

	@Autowired
	CocheraDao cocheraDao;

	@Override
	protected CocheraDao getService() {
		return cocheraDao;
	}

	@RequestMapping(value = "/plantas", method = RequestMethod.GET)
	public @ResponseBody List<Integer> plantas() {
		return getService().findCocheras();
	}
	
	@RequestMapping(value = "/{floor}/cocheras", method = RequestMethod.GET)
	public @ResponseBody List<Cochera> cocheras(@PathVariable Integer floor) {
		return getService().findAllByPlanta(floor);
	}
	@RequestMapping(value = "/guardarEnrocar", method = RequestMethod.POST)
	public @ResponseBody Cochera guardarEnrocar(@RequestBody Cochera cocheraNueva) {
		Cochera cocheraReal = getService().findOne(cocheraNueva.getId());
		if (cocheraReal.getVehiculo() != null && cocheraReal.getVehiculo().getId() != cocheraNueva.getVehiculo().getId()) {
			Cochera cocheraVieja = getService().findOneByVehiculo(cocheraNueva.getVehiculo());
			cocheraVieja.setVehiculo(cocheraReal.getVehiculo());
			getService().save(cocheraVieja);
		}
		getService().save(cocheraNueva);
		onUpdateSuccess(cocheraNueva);
		return cocheraNueva;
	}

}
