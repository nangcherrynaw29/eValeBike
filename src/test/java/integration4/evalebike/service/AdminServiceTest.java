package integration4.evalebike.service;

import integration4.evalebike.domain.Administrator;
import integration4.evalebike.domain.Company;
import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Administrator admin;

    @BeforeEach
    public void setUp() {
        admin = new Administrator();
        admin.setId(1);
        admin.setName("John Doe");
        admin.setEmail("john@example.com");
        Company company = new Company();
        company.setName("Bike Co.");
        admin.setCompany(company);
    }

    @Test
    public void testGetAllAdmins() {
        // Arrange
        when(adminRepository.findAll()).thenReturn(List.of(admin));

        // Act
        var result = adminService.getAllAdmins();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testGetAdminById_WhenAdminExists() {
        // Arrange
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));

        // Act
        var result = adminService.getAdminById(1);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testGetAdminById_WhenAdminDoesNotExist() {
        // Arrange
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> adminService.getAdminById(1));
    }

    @Test
    public void testSaveAdmin() {
        // Arrange
        Company company = new Company();
        company.setName("Bike Co.");  // Initialize the company

        when(adminRepository.save(any(Administrator.class))).thenReturn(admin);

        // Act
        var result = adminService.saveAdmin("John Doe", "john@example.com", company, 4);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("Bike Co.", result.getCompany().getName());
        assertEquals(4, result.getCreatedBy().getId());
    }

    @Test
    public void testUpdateAdmin_WhenAdminExists() {
        // Arrange
        Administrator updatedAdmin = new Administrator();
        updatedAdmin.setId(1);
        updatedAdmin.setName("Jane Doe");
        updatedAdmin.setEmail("jane@example.com");
        Company company = new Company();
        company.setName("Bike Co.");
        updatedAdmin.setCompany(company);

        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Administrator.class))).thenReturn(updatedAdmin);

        // Act
        var result = adminService.updateAdmin(1, updatedAdmin);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("New Bike Co.", result.getCompany().getName());
    }

    @Test
    public void testUpdateAdmin_WhenAdminDoesNotExist() {
        // Arrange
        Administrator updatedAdmin = new Administrator();
        updatedAdmin.setId(1);
        updatedAdmin.setName("Jane Doe");
        updatedAdmin.setEmail("jane@example.com");
        Company company = new Company();
        company.setName("Bike Co.");
        updatedAdmin.setCompany(company);

        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> adminService.updateAdmin(1, updatedAdmin));
    }

    @Test
    public void testDeleteAdmin_WhenAdminExists() {
        // Arrange
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin));
        doNothing().when(adminRepository).deleteById(1);

        // Act
        adminService.deleteAdmin(1);

        // Assert
        verify(adminRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteAdmin_WhenAdminDoesNotExist() {
        // Arrange
        when(adminRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> adminService.deleteAdmin(1));
    }
}
