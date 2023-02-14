package business;

import business.exception.RegistroJaExistenteException;
import business.exception.TipoPessoaDiferenteEsperadoException;
import model.*;
import persistence.Repository;

import java.time.LocalDateTime;

public class GerenciadorDeLocacaoPJ extends GerenciadorDeLocacao{
    public GerenciadorDeLocacaoPJ(Repository<Locacao> repositoryDeLocacao) {
        super(repositoryDeLocacao);
    }

    @Override
    public Locacao adicionarLocacao(Veiculo veiculo, Cliente cliente, LocalDateTime dataInicioAlocacao, LocalDateTime dataFinalAlocacao){
        verificaVeiculoLiberado(veiculo, dataInicioAlocacao, dataFinalAlocacao);
        verificaTipoPessoaCliente(cliente);
        Locacao novaLocacao = new LocacaoPJ(cliente,veiculo,dataInicioAlocacao,dataFinalAlocacao);
        Locacao locacaoSalva = getRepositoryDeLocacao().salvar(novaLocacao);
        return locacaoSalva;
    }

    @Override
    public Locacao adicionarLocacao(Veiculo veiculo, Cliente cliente, LocalDateTime dataFinalAlocacao) {
        LocalDateTime dataInicioAlocacao = LocalDateTime.now();
        verificaVeiculoLiberado(veiculo, dataInicioAlocacao, dataFinalAlocacao);
        verificaTipoPessoaCliente(cliente);
        Locacao novaLocacao = new LocacaoPJ(cliente,veiculo,dataInicioAlocacao,dataFinalAlocacao);
        Locacao locacaoSalva = getRepositoryDeLocacao().salvar(novaLocacao);
        return locacaoSalva;
    }

    private void verificaVeiculoLiberado(Veiculo veiculo, LocalDateTime dataInicioAlocacao, LocalDateTime dataFinalAlocacao) {
        if (existeLocacaoNoPeriodo(veiculo, dataInicioAlocacao, dataFinalAlocacao)){
            throw new RegistroJaExistenteException(LOCACAO_DESCRICAO_CLASSE, veiculo.getModelo()+" na data");
        }
    }
    @Override
    public void verificaTipoPessoaCliente(Cliente cliente){
        if (cliente.getTipoDocumento().equals(TipoDocumento.CPF)){
            throw new TipoPessoaDiferenteEsperadoException("CNPJ","CPF","GerenciadorDeLocacaoPJ");
        }
    }
}
