package br.com.newfood.pagamentos.service;

import br.com.newfood.pagamentos.dto.PagamentoDto;
import br.com.newfood.pagamentos.model.Pagamento;
import br.com.newfood.pagamentos.model.Status;
import br.com.newfood.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;
    @Autowired
    private ModelMapper mapper;

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {

        return repository.findAll(paginacao).map(pagamento -> mapper.map(pagamento, PagamentoDto.class));

    }

    public PagamentoDto obterPorId(Long id) {

        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        return mapper.map(pagamento, PagamentoDto.class);

    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {

        Pagamento pagamento = mapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return mapper.map(pagamento, PagamentoDto.class);

    }

    public PagamentoDto alterarPagamento(Long id, PagamentoDto dto) {

        Pagamento pagamento = mapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);

        return mapper.map(pagamento, PagamentoDto.class);

    }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }

}
