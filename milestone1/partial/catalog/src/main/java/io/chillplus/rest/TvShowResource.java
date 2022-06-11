package io.chillplus.rest;

import io.chillplus.domain.TvShow;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/tv")
@Produces("application/json")
@Consumes("application/json")
public class TvShowResource {

    private long sequence = 0;
    @POST
    @Transactional
    public Response create(@Valid TvShow tvShow) {

        if (tvShow.id != null) {
            throw new WebApplicationException("A new entity cannot already have an ID", Response.Status.BAD_REQUEST);
        }
        tvShow.persist();
        return Response.status(Response.Status.CREATED).entity(tvShow).build();
    }

    @GET
    public List<TvShow> getAll() {

        return TvShow.findByOrderTille();
    }

    @GET
    @Path("/{id}")
    public Response getOneById(@PathParam("id") long id) {
     TvShow find    =   TvShow.findById(id);
     if (find !=null)
         return Response.ok(find).build();
     return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Transactional
    public Response deleteAll() {
        TvShow.deleteAll();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id) {
        boolean deleted = TvShow.deleteById(id);
        if (deleted)
        return Response.ok().build();

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Transactional
    public Response update(@Valid TvShow tvShow)
    {
        if (tvShow.id ==null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        TvShow findObject = TvShow.findById(tvShow.id);
        if (findObject !=null)
        {
            findObject = tvShow;
            return Response.ok(findObject).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();


    }
    @GET
    @Path("/search")
    public Response getOneByTitle(@QueryParam("title") String title)
    {
        TvShow findObject = TvShow.findByTitle(title);
        if (findObject !=null)
        {
            return Response.ok(findObject).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @GET
    @Path("/categories/{category}")
    public Response getAllByCategory(@PathParam("category") String category,
                                     @QueryParam("pageIndex") int pageIndex,
                                     @QueryParam("pageSize") int pageSize)
    {
        List<TvShow> findObject = TvShow.findByCategoryIgnoreCase(category,pageSize,pageIndex);
        if (findObject !=null)
        {
            return Response.ok(findObject).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
