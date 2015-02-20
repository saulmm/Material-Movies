import com.hackvg.domain.ConfigurationUsecase;
import com.hackvg.domain.ConfigurationUsecaseController;
import com.hackvg.model.MediaDataSource;
import com.hackvg.model.entities.ConfigurationResponse;
import com.hackvg.model.entities.PopularMoviesApiResponse;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by saulmm on 19/02/15.
 */
public class GetConfigurationUsecase {

    // Class under test
    private ConfigurationUsecase configurationUsecase;

    @Mock
    private MediaDataSource mockDataSource;

    @Mock
    private Bus mockUiBus;

    @Before
    public void setUp () {

        MockitoAnnotations.initMocks(this);

        configurationUsecase = new ConfigurationUsecaseController(
            mockDataSource, mockUiBus);
    }

    @Test
    public void testConfigurationRequestExecution () {

        configurationUsecase.execute();

        verify(mockDataSource, times(1))
            .getConfiguration();
    }

    @Test
    public void testConfigurationPost () {

        configurationUsecase.onConfigurationReceived(
            new ConfigurationResponse());

        verify(mockUiBus, times(1)).post(
            Mockito.any(PopularMoviesApiResponse.class));
    }
}
