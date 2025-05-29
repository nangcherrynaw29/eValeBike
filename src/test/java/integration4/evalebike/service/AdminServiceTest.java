package integration4.evalebike.service;

import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.Company;
import integration4.evalebike.domain.UserStatus;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.AdminRepository;
import integration4.evalebike.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Administrator admin;

    @BeforeEach
    void setUp() {
        admin = new Administrator();
        admin.setId(1);
        admin.setName("Alice Johnson");
        admin.setEmail("alice.johnson@example.com");
        admin.setUserStatus(UserStatus.APPROVED);

        Company company = new Company();
        company.setId(1);
        company.setName("E-Bike Corp");
        admin.setCompany(company);
    }

    @Test
    void getAllAdmins_shouldReturnApprovedAdmins() {
        Administrator another = new Administrator();
        another.setName("Bob Brown");
        another.setEmail("bob.brown@example.com");

        Company company = new Company();
        company.setName("E-Nursing");
        another.setCompany(company);

        List<Administrator> admins = Arrays.asList(admin, another);
        when(adminRepository.findByUserStatus(UserStatus.APPROVED)).thenReturn(admins);

        List<Administrator> result = adminService.getAllAdmins();

        assertEquals(2, result.size());
        assertEquals("Alice Johnson", result.get(0).getName());
        verify(adminRepository).findByUserStatus(UserStatus.APPROVED);
    }

    @Test
    void getAllAdmins_shouldReturnEmptyListWhenNoApprovedAdmins() {
        when(adminRepository.findByUserStatus(UserStatus.APPROVED)).thenReturn(List.of());

        List<Administrator> result = adminService.getAllAdmins();

        assertTrue(result.isEmpty());
        verify(adminRepository).findByUserStatus(UserStatus.APPROVED);
    }

    @Test
    void getAdminById_shouldReturnAdminWhenFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        Administrator result = adminService.getAdminById(1);

        assertNotNull(result);
        assertEquals("Alice Johnson", result.getName());
        verify(adminRepository).findById(1);
    }

    @Test
    void getAdminById_shouldThrowNotFoundExceptionWhenAdminNotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> adminService.getAdminById(1));
        assertEquals("Admin with ID 1 was not found.", exception.getMessage());
        verify(adminRepository).findById(1);
    }

    @Test
    void updateAdmin_shouldUpdateExistingAdmin() {
        Administrator updatedDetails = new Administrator();
        updatedDetails.setName("Nora Updated");
        updatedDetails.setEmail("nora.updated@example.com");

        Company updatedCompany = new Company();
        updatedCompany.setName("E-Nursing Updated");
        updatedDetails.setCompany(updatedCompany);

        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Administrator.class))).thenReturn(admin);

        Administrator result = adminService.updateAdmin(1, updatedDetails);

        assertEquals("Nora Updated", result.getName());
        assertEquals("nora.updated@example.com", result.getEmail());
        assertEquals("E-Nursing Updated", result.getCompany().getName());
    }

    @Test
    void updateAdmin_shouldThrowRuntimeExceptionWhenAdminNotFound() {
        Administrator updatedDetails = new Administrator();
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.updateAdmin(1, updatedDetails));
        assertEquals("Admin not found", exception.getMessage());
        verify(adminRepository).findById(1);
        verify(adminRepository, never()).save(any());
    }

    @Test
    void deleteAdmin_shouldDeleteExistingAdmin() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        doNothing().when(adminRepository).deleteById(1);

        adminService.deleteAdmin(1);

        verify(adminRepository).deleteById(1);
    }

    @Test
    void deleteAdmin_shouldThrowRuntimeExceptionWhenAdminNotFound() {
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.deleteAdmin(1));
        assertEquals("Admin not found", exception.getMessage());
        verify(adminRepository).findById(1);
        verify(adminRepository, never()).deleteById(any());
    }
}
