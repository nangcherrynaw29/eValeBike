package integration4.evalebike.service;

import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.AdminRepository;
import integration4.evalebike.utility.PasswordUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordUtility passwordUtility;

    @InjectMocks
    private AdminService adminService;

    private Administrator admin;

    @BeforeEach
    public void setUp() {
        admin = new Administrator();
        admin.setId(1);
        admin.setName("John Doe");
        admin.setEmail("john@example.com");
        admin.setCompanyName("Bike Co.");
        admin.setUserStatus(UserStatus.APPROVED);
    }

    @Test
    public void testGetAllAdmins() {
        when(adminRepository.findByUserStatus(UserStatus.APPROVED)).thenReturn(List.of(admin));

        List<Administrator> result = adminService.getAllAdmins();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testGetAdminById_Found() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        Administrator result = adminService.getAdminById(1);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testGetAdminById_NotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> adminService.getAdminById(1));
    }

    @Test
    public void testSaveAdmin() {
        when(passwordUtility.generateRandomPassword(8)).thenReturn("plain123");
        when(passwordUtility.hashPassword("plain123")).thenReturn("hashed123");
        when(adminRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator result = adminService.saveAdmin("John Doe", "john@example.com", "Bike Co.");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("Bike Co.", result.getCompanyName());
        assertEquals("hashed123", result.getPassword());

        verify(passwordUtility).sendPasswordEmail("john@example.com", "plain123");
    }

    @Test
    public void testUpdateAdmin_Found() {
        Administrator updatedAdmin = new Administrator();
        updatedAdmin.setName("Jane Doe");
        updatedAdmin.setEmail("jane@example.com");
        updatedAdmin.setCompanyName("New Co.");

        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Administrator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Administrator result = adminService.updateAdmin(1, updatedAdmin);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("New Co.", result.getCompanyName());
    }

    @Test
    public void testUpdateAdmin_NotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        Administrator updatedAdmin = new Administrator();
        updatedAdmin.setName("Jane");

        assertThrows(RuntimeException.class, () -> adminService.updateAdmin(1, updatedAdmin));
    }

    @Test
    public void testDeleteAdmin_Found() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        doNothing().when(adminRepository).deleteById(1);

        adminService.deleteAdmin(1);

        verify(adminRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAdmin_NotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.deleteAdmin(1));
    }
}
