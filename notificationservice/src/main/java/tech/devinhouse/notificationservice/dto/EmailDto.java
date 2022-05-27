package tech.devinhouse.notificationservice.dto;

public class EmailDto {
    private String destinatario;

    private String titulo;

    private String mensagem;

    public EmailDto(String destinatario, String titulo, String mensagem) {
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public EmailDto() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
