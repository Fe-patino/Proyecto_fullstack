package com.resenias.resenias.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PedidoClient {

    private final RestTemplate restTemplate;

    // URL del ms-pedidos (configurada en application.properties)
    @Value("${ms.pedidos.url}")
    private String pedidosUrl;

    // Verifica si un pedido existe y su estado es ENTREGADO
    public boolean pedidoEntregado(Integer pedidoId) {
        try {
            String url = pedidosUrl + "/api/pedidos/" + pedidoId;
            Map response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                String estado = (String) response.get("estado");
                return "ENTREGADO".equals(estado);
            }
            return false;
        } catch (HttpClientErrorException.NotFound e) {
            // El pedido no existe
            return false;
        } catch (Exception e) {
            // Si ms-pedidos no esta disponible permitimos la resenia
            return true;
        }
    }
}
