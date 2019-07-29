package edu.cnm.deepdive.qod.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import edu.cnm.deepdive.qod.SpringRestDocsApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = SpringRestDocsApplication.class)
class QuoteControllerTest {

  private MockMvc mockMvc;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).
        apply(documentationConfiguration(provider)).
        alwaysDo(document("{method-name}", preprocessRequest(prettyPrint())
            , preprocessResponse(prettyPrint()))).
        build();
  }

  @Test
  void getRandom() throws Exception {
    mockMvc.perform(get("/quotes/random"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(document("get-random",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
//            links(linkWithRel("self").description("URL of quote")),
            responseHeaders(headerWithName("Content-Type").description("Content type of payload"))
        ));
  }

}