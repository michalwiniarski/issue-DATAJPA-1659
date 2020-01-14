package de.schauderhaft.datajpa1659;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class Datajpa1659ApplicationTests {

	@Autowired
	TransactionTemplate tx;

	@Autowired
	BRepository bRepo;
	@Autowired
	ARepository aRepo;

	@Test
	void deleteAll() {

		tx.executeWithoutResult((ts) -> {

			B b = new B(createRandomId());
			bRepo.save(b);

			A a = new A(createRandomId());
			a.setB(b);
			aRepo.save(a);
		});


		tx.executeWithoutResult((ts) -> {

			assertThat(aRepo.findAll()).hasSize(1);
			assertThat(bRepo.findAll()).hasSize(1);

		});
		tx.executeWithoutResult((ts) -> {

			aRepo.deleteAll();
			bRepo.deleteAll();

			assertThat(aRepo.findAll()).hasSize(0);
			assertThat(bRepo.findAll()).hasSize(0);

		});


	}

	private EntityId createRandomId() {
		return new EntityId(UUID.randomUUID().toString());
	}

}
