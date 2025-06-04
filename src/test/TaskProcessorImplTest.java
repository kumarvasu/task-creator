package test;

import com.task.agentic.dto.AdGroupDetailDto;
import com.task.agentic.services.TaskProcessorImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class TaskProcessorImplTest {

    @InjectMocks
    private TaskProcessorImpl taskProcessor;

    @Test
    void testGetAdGroupDetail() {
        String fileContent = "groupId=123\ngroupName=Test Group";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());

        try (MockedStatic<ClassLoader> mockedClassLoader = mockStatic(ClassLoader.class)) {
            mockedClassLoader.when(() -> 
                TaskProcessorImpl.class.getClassLoader().getResourceAsStream("adGroupDetail.txt")
            ).thenReturn(inputStream);

            AdGroupDetailDto result = taskProcessor.getAdGroupDetail();

            assertEquals("123", result.getGroupId());
            assertEquals("Test Group", result.getGroupName());
        }
    }
}