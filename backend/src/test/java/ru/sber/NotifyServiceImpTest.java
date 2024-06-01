package ru.sber;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.entities.Notify;
import ru.sber.entities.User;
import ru.sber.entities.enums.EStatusEmployee;
import ru.sber.repositories.NotifyRepository;
import ru.sber.services.NotifyServiceImp;
import ru.sber.services.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotifyServiceImpTest {

    @Autowired
    private NotifyServiceImp notifyServiceImp;

    @MockBean
    private NotifyRepository notifyRepository;

    @MockBean
    private UserService userService;

    @Test
    public void testGettingNotify() {
        Long id = 120L;
        lenient().when(notifyRepository.existsById(id)).thenReturn(false);
    }

    @Test
    public void testAddNotify() {
        Long id = 1L;

        User user = new User();
        user.setStatus(EStatusEmployee.ACTIVE);
        Mockito.when(userService.getUser()).thenReturn(user);
        Mockito.when(notifyRepository.existsByIdOrder(id)).thenReturn(false);

        notifyServiceImp.addNotify(id);

        Mockito.verify(notifyRepository, Mockito.times(1)).save(new Notify(id));
    }

    @Test
    public void testGetEmptyList() {
        String result = notifyRepository.findAll().toString();
        assertEquals("[]", result);
    }

    @Test
    public void testGetListId() {
        List<Notify> notifyList = Arrays.asList(new Notify(1L), new Notify(2L));
        User user = new User();
        user.setStatus(EStatusEmployee.ACTIVE);

        Mockito.when(userService.getUser()).thenReturn(user);
        Mockito.when(notifyRepository.findAll()).thenReturn(notifyList);

        String result = notifyServiceImp.getListId();

        Assert.assertEquals("1,2", result);

        Mockito.verify(notifyRepository, Mockito.times(1)).deleteAll();
    }

}
