package ca.ulaval.glo4002.cafe.api.menu;

import ca.ulaval.glo4002.cafe.api.menu.request.NewCoffeeRequest;
import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.application.menu.parameter.NewCoffeeParams;

import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {
    private final MenuService menuService;

    public MenuResource(MenuService menuService) {
        this.menuService = menuService;
    }

    @POST
    @Path("")
    public Response addCoffeeToMenu(@Valid NewCoffeeRequest newCoffeeRequest) {
        NewCoffeeParams newCoffeeParams =
            NewCoffeeParams.from(newCoffeeRequest.name, newCoffeeRequest.cost, newCoffeeRequest.ingredients.Chocolate, newCoffeeRequest.ingredients.Milk,
                newCoffeeRequest.ingredients.Water, newCoffeeRequest.ingredients.Espresso);
        menuService.addCoffeeToMenu(newCoffeeParams);

        return Response.ok().build();
    }
}
