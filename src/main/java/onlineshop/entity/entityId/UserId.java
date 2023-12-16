package onlineshop.entity.entityId;

import java.util.Objects;

public record UserId(long id) {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserId userId = (UserId) o;
    return id == userId.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
