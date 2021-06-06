package br.com.anderson;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RestApiTest {
	
	@BeforeClass
	public static void setup() {
	
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured
		.given()
		.log().all()
		.when()
		    .get("/todo")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void deveAdicinarTarefaComSucesso() {
		RestAssured
		.given()
		.log().all()
		.body("{\"task\":\"nova descrição\", \"dueDate\":\"2022-10-10\"}")
		.contentType(ContentType.JSON)
		.when()
		    .post("/todo")
		.then()
		.statusCode(201)
		.log().all();
	}
	
	@Test
	public void naoDeveAdicinarTarefaInvalida() {
		RestAssured
		.given()
		.body("{\"task\":\"nova descrição\", \"dueDate\":\"2010-10-10\"}")
		.contentType(ContentType.JSON)
		.when()
		    .post("/todo")
		.then()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"));
	}
	

}
