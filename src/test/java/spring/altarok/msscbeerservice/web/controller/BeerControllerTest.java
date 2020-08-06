package spring.altarok.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import spring.altarok.msscbeerservice.bootstrap.BeerLoader;
import spring.altarok.msscbeerservice.services.BeerService;
import spring.altarok.msscbeerservice.web.model.BeerDto;
import spring.altarok.msscbeerservice.web.model.BeerStyleEnum;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerDto = BeerDto.builder()
                .beerName("BeerGarden")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("19.35"))
                .quantityOnHand(15)
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get")
                        ),
                        responseFields(
                                fields.withPath("id").description("ID of the beer"),
                                fields.withPath("version").description("API Version of the beer"),
                                fields.withPath("createdDate").description("Creation Date"),
                                fields.withPath("lastModifiedDate").description("Last Modification Date"),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of the beer"),
                                fields.withPath("upc").description("upc value of the beer"),
                                fields.withPath("price").description("Price of the beer"),
                                fields.withPath("quantityOnHand").description("Quantity of the beer on hand")
                       )));
    }

    @Test
    void saveNewBeer() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        given(beerService.saveNewBeer(any(BeerDto.class))).willReturn(beerDto);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-post",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of the beer"),
                                fields.withPath("upc").description("upc value of the beer").attributes(),
                                fields.withPath("price").description("Beer price"),
                                fields.withPath("quantityOnHand").ignored()
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.updateBeer(any(UUID.class), any(BeerDto.class))).willReturn(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}