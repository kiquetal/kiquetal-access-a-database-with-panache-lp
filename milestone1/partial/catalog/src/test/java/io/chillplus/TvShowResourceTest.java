package io.chillplus;

import io.chillplus.domain.TvShow;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TvShowResourceTest {

    public static final String DEFAULT_TITLE = "AAA";

    @BeforeEach
    public void beforeEach() {
        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);
    }

    @Test
    public void createTvShow() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        TvShow tvShowWithId = new TvShow();
        tvShow.id = 1L;
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShowWithId)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);
    }

    @Test
    public void checkTvShowTitleIsNotBlank() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = "";

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    public void getAllTvShows() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow bbShow = new TvShow();
        bbShow.title = "AA";

        given()
                .body(bbShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(bbShow.title));

        TvShow aaShow = new TvShow();
        aaShow.title = "BB";

        given()
                .body(aaShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(aaShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(2));
    }

    @Test
    public void getOneTvShow() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));


        given()
                .when()
                .get("/api/tv/{id}", 1)
                .then()
                .statusCode(200);
    }

    @Test
    public void getNonExistingTvShow() {
        given()
                .when()
                .get("/api/tv/42")
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteAllTvShows() {
        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    public void deleteOneTvShow() {
        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    public void updateTvShow()
    {
        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;
      TvShow tvShow1=  given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title))
                .extract().as(TvShow.class);
      Long id = tvShow1.id;
      tvShow.id = id;
      tvShow.title = "CCC";
        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .put("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("title", Matchers.is("CCC"));

        tvShow.id =null;
        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .put("/api/tv")
                .then()
                .statusCode(400);


    }
    @Test
    public void getTvShowByTitle() {

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;
        TvShow tvShow1=  given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title))
                .extract().as(TvShow.class);

        given()
                .queryParam("title", DEFAULT_TITLE)
                .get("/api/tv/search")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow1.title));


    }

    @Test
    public void getAllTvShowsByCategory()
    {

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;
        tvShow.category = "Comedy";
         given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));
         tvShow.title= "CCC";
         tvShow.category = "Comedy";
        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));
        tvShow.title= "DDD";
        tvShow.category = "Comedy";
        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .queryParam("pageSize", 3)
                .queryParam("pageIndex", 0)
                .get("/api/tv/categories/{category}", "Comedy")
                .then()
                .body("$.size()", is(3));


    }

}
