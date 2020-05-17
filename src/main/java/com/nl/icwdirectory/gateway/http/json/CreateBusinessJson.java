package com.nl.icwdirectory.gateway.http.json;

import com.nl.icwdirectory.domain.Address;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateBusinessJson {

  @NotNull
  private String name;
  @NotNull
  private String ownerFirstName;
  private String ownerLastName;
  private Address address;
  @NotNull
  private String email;
  private String website;
  @NotNull
  private String phone;
  private String logo;
  private List<String> images;
  private String description;
  private List<String> tags;

}
