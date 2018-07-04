package com.jcg.spring.batch;

import lombok.Setter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.AbstractResource;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


@Setter
public class CheckUploadDirTasklet implements Tasklet {

    private AbsractResourceWithChilds uploadDir;
    public static String ERROR_SUFFIX = ".error";
    public static String READY_SUFFIX = ".ready";
    public static String SKIP_SUFFIX = ".skip";



    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        if (!uploadDir.exists()) {
            //TODO LOG that fil
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }
        List<AbsractResourceWithChilds> childResources = uploadDir.getChildResources();

        Set<String> allFiles = childResources.stream()
                .filter(resource -> !resource.isDirectory())
                .map(AbstractResource::getFilename)
                .collect(Collectors.toSet());

        Set<String> readyFiles = allFiles.stream()
                .filter(name-> name.contains(READY_SUFFIX))
                .map(name->name.replace(READY_SUFFIX,""))
                .collect(Collectors.toSet());

        /*Выбираем только те файлы, у которых есть и сам файл и пустой файл с этим же навзанием и индексом READY_SUFFIX:*/
        readyFiles.retainAll(allFiles);

        if (readyFiles.isEmpty()) {
            // TODO: 6/29/2018 no files to upload
            stepContribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }
        Queue<String> filesToUpload = new LinkedList<>(readyFiles);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("fileToDownload", filesToUpload);
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("nextFile",filesToUpload.remove());

        return RepeatStatus.FINISHED;
    }

    private void markAsSkipped(Set<String> files) {
        // TODO: 6/29/2018 doubledFiles 
    }
}
