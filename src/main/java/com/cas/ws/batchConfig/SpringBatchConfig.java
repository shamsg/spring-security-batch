package com.cas.ws.batchConfig;

import com.cas.ws.io.repositories.TrxHistoryRepository;
import com.cas.ws.io.entity.TrxHistory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

	@Autowired
	private TrxHistoryRepository trxHistoryRepository;

	private LineMapper<TrxHistory> lineMapperTrxHistory() {
		DefaultLineMapper<TrxHistory> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter("|");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");

		BeanWrapperFieldSetMapper<TrxHistory> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(TrxHistory.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
	}

	@Bean
	public FlatFileItemReader<TrxHistory> readerTrxHistory() {
		return new FlatFileItemReaderBuilder<TrxHistory>()
				.name("trxHistoryReader")
				.resource(new ClassPathResource("dataSource.txt"))
				.linesToSkip(1)
				.lineMapper(lineMapperTrxHistory())
				.targetType(TrxHistory.class)
				.build();
	}

	@Bean
	TrxHistoryProcessor processorTrxHisotry() {
		return new TrxHistoryProcessor();
	}

	@Bean
	RepositoryItemWriter<TrxHistory> writerTrxHistory() {
		RepositoryItemWriter<TrxHistory> writer = new RepositoryItemWriter<>();
		writer.setRepository(trxHistoryRepository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Job jobTrxHistory(JobRepository jobRepository, Step step) {
		return new JobBuilder("importFrom txt", jobRepository)
				.start(step)
				.build();
	}

	@Bean
	public Step stepTrxHistory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("txt-import-step", jobRepository)
				.<TrxHistory, TrxHistory>chunk(10, transactionManager)
				.reader(readerTrxHistory())
				.processor(processorTrxHisotry())
				.writer(writerTrxHistory())
				.build();
	}





	@Bean
	public FlatFileItemReader<TrxHistory> reader() {
		/*
		FlatFileItemReader<TrxHistory> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("dataSource.txt"));
		itemReader.setName("txtReader");
		itemReader.setLinesToSkip(1);
		itemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy() {
			@Override
			public boolean isEndOfRecord(String line) {
				// Only treat non-empty lines as valid records
				return line != null && !line.trim().isEmpty();
			}

			@Override
			public String postProcess(String record) {
				return record.trim();
			}
		});
		itemReader.setLineMapper(lineMapper());
		return itemReader;
		 */

		return new FlatFileItemReaderBuilder<TrxHistory>()
				.name("trxHistory")
				.resource(new ClassPathResource("dataSource.txt"))
				.linesToSkip(1)
				.lineMapper(lineMapper())
				.targetType(TrxHistory.class)

				.build();

	}
	private LineMapper<TrxHistory> lineMapper() {
		DefaultLineMapper<TrxHistory> lineMapper = new DefaultLineMapper<>();
/*
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter("|");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");


 */

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter("|");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");

		BeanWrapperFieldSetMapper<TrxHistory> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(TrxHistory.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);


		return lineMapper;
	}



	@Bean
	RepositoryItemWriter<TrxHistory> writer() {
		RepositoryItemWriter<TrxHistory> writer = new RepositoryItemWriter<>();
		writer.setRepository(trxHistoryRepository);
		writer.setMethodName("save");
		return writer;
	}


	@Bean
	public Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("importTrxHistory", jobRepository)
				.start(step)
				.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("txt-import-step", jobRepository)
				.<TrxHistory, TrxHistory>chunk(10, transactionManager)
				.reader(reader())
				.processor(processorTrxHisotry())
				.writer(writer())
				.build();
	}


	/*

	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>()
				.name("personItemReader")
				.resource(new ClassPathResource("people-1000.csv"))
				.linesToSkip(1)
				.lineMapper(lineMapper())
				.targetType(Person.class)
				.build();
	}
	private LineMapper<Person> lineMapper() {
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "userId", "firstName", "lastName", "gender", "email", "phone", "dateOfBirth", "jobTitle");

		BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Person.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
	}

	@Bean
	PersonProcessor processor() {
		return new PersonProcessor();
	}

	@Bean
	RepositoryItemWriter<Person> writer() {
		RepositoryItemWriter<Person> writer = new RepositoryItemWriter<>();
		writer.setRepository(personRepository);
		writer.setMethodName("save");
		return writer;
	}


	@Bean
	public Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("importPersons", jobRepository)
				.start(step)
				.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("csv-import-step", jobRepository)
				.<Person, Person>chunk(10, transactionManager)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	 */

}
