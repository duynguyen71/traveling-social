package com.tv.socket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

//    Topics – common conversations or chat topics open to any client or user
//    Queues – reserved for specific users and their current sessions
//    Endpoints – generic endpoints

    private final RestTemplate restTemplate;

    public WebSocketConfiguration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // prefix của các endpoint mà các client có thể subscribe và nhận message từ server
        // topic PUB-SUB
        // queue PRIVATE
        registry.enableSimpleBroker("/queue", "/topic");
        // prefix cho các destination mà client sẽ gửi message
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/users");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/tc-socket");
    }


//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(
//                new ChannelInterceptor() {
//                    @Override
//                    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                        StompHeaderAccessor accessor =
//                                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
////                        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
////                            String jwtToken = accessor.getFirstNativeHeader("Authorization");
////                            log.info(jwtToken);
////                            HttpHeaders headers = new HttpHeaders();
////                            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
////                            headers.set("Authorization", jwtToken);
////                            HttpEntity<String> entity = new HttpEntity<String>(headers);
//                        //luu tin nhan
//
////                        ResponseEntity<BaseResponse> responseObj =
////                                    restTemplate.exchange("http://localhost:8081/api/v1/member/users/me", HttpMethod.GET, entity, BaseResponse.class);
//
//                            //MessageResponse.class
////                            => tra ve cho user
//////
//////                           log.info(data.toString());
////                            if (responseObj.getBody().getStatus() == 200) {
//                        accessor.setUser(new MyUserPrinciple("kddevit"));
////                            }
//
////                        }
//                        return message;
//                    }
//                }
//        );
//    }
}
