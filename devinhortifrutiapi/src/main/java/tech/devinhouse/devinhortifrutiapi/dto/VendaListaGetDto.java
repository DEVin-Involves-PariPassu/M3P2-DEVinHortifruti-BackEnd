package tech.devinhouse.devinhortifrutiapi.dto;

import java.util.List;

public class VendaListaGetDto {

    private Long totalElementos;

    private Integer totalPaginas;

    private List<VendaElementoGetDto> vendas;

    public VendaListaGetDto(Long totalElementos, Integer totalPaginas, List<VendaElementoGetDto> vendas) {
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
        this.vendas = vendas;
    }

    public VendaListaGetDto() {
    }

    public Long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(Long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public List<VendaElementoGetDto> getVendas() {
        return vendas;
    }

    public void setVendas(List<VendaElementoGetDto> vendas) {
        this.vendas = vendas;
    }
}
