package integration4.evalebike.service;

import integration4.evalebike.controller.technician.dto.BikeDto;
import integration4.evalebike.domain.*;

import integration4.evalebike.exception.NotFoundException;
import integration4.evalebike.repository.*;
import integration4.evalebike.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class BikeOwnerService {
    private final BikeOwnerRepository bikeOwnerRepository;
    private final BikeOwnerBikeRepository bikeOwnerBikeRepository;
    private final BikeRepository bikeRepository;
    private final QrCodeService qrCodeService;
    private final PasswordService passwordService;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final EmailService emailService;

    public BikeOwnerService(BikeOwnerRepository bikeOwnerRepository, BikeOwnerBikeRepository bikeOwnerBikeRepository, BikeRepository bikeRepository, QrCodeService qrCodeService, PasswordService passwordService, UserRepository userRepository, EmailService emailService, CompanyRepository companyRepository) {
        this.bikeOwnerRepository = bikeOwnerRepository;
        this.bikeOwnerBikeRepository = bikeOwnerBikeRepository;
        this.bikeRepository = bikeRepository;
        this.qrCodeService = qrCodeService;
        this.passwordService = passwordService;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.emailService = emailService;
    }

    public List<BikeOwner> getAll(CustomUserDetails currentUser) {
        if (currentUser.getRole() == Role.SUPER_ADMIN) {
            return bikeOwnerRepository.findByUserStatus(UserStatus.APPROVED);
        } else {
            return bikeOwnerRepository.findByUserStatusAndCompany(
                    UserStatus.APPROVED,
                    currentUser.getCompany()
            );
        }
    }

    public BikeOwner add(String name, String email, String phoneNumber, LocalDate birthDate, int createdBy, Integer companyId) {
        String rawPassword = passwordService.generateRandomPassword(8);
        String hashedPassword = passwordService.hashPassword(rawPassword);
        BikeOwner bikeOwner = new BikeOwner(name, email, phoneNumber, birthDate);
        bikeOwner.setPassword(hashedPassword);
        bikeOwner.setUserStatus(UserStatus.APPROVED);
        bikeOwner.setCreatedBy(userRepository.findById(createdBy).orElseThrow((() -> NotFoundException.forTechnician(createdBy))));
        if (companyId != null) {
            Company company = companyRepository.findById(companyId).orElse(null);
            bikeOwner.setCompany(company);
        } else {
            bikeOwner.setCompany(userRepository.findById(createdBy).orElseThrow((() -> NotFoundException.forTechnician(createdBy))).getCompany());
        }
        emailService.sendPasswordEmailtoBikeOwner(email, rawPassword);
        return bikeOwnerRepository.save(bikeOwner);
    }

    public List<BikeOwner> getFiltered(String name, String email) {
        return bikeOwnerRepository.findByFilters(name, email);
    }

    public List<BikeDto> getAllBikes(Integer bikeOwnerId) {
        List<Bike> bikes = bikeOwnerBikeRepository.findByBikeOwnerId(bikeOwnerId)
                .stream()
                .map(BikeOwnerBike::getBike)
                .collect(Collectors.toList());

        bikes.forEach(bike -> {
            String qrImage = qrCodeService.generateQrCodeBase64(bike.getBikeQR(), 200, 200);
            bike.setQrCodeImage(qrImage); // Set the transient field, not the ID
        });

        return bikes.stream().map(BikeDto::fromBike).collect(Collectors.toList());
    }

    public void delete(Integer id) {
        BikeOwner bikeOwner = bikeOwnerRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forBikeOwner(id));
        bikeOwnerBikeRepository.deleteByBikeOwnerId(id);
        bikeOwnerRepository.delete(bikeOwner);
    }

    public long countOwnersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        return bikeOwnerRepository.countByBirthdayToday(today.getMonthValue(), today.getDayOfMonth());
    }
}