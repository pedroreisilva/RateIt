package pt.ipg.rateit;

public class Filmes {

    private int id;
    private String nome;
    private String nota;
    private int idCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void getNome(String title) {
        this.nome = title;
    }

    public String getNota() {
        return nota;
    }

    public void getNota(String price) {
        this.nota = price;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
