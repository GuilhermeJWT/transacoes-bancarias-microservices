package br.com.systemsgs.transactionservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
class TransactionServiceApplicationTests {

	@Test
	void testMain(CapturedOutput output) {
		TransactionServiceApplication.main(new String[]{});

		assertTrue(output.getOut().contains("Started TransactionServiceApplication"));
	}
}
