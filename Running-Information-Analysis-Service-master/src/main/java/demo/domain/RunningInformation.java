package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "private")
@Data
public class RunningInformation {
	public enum HealthWarningLevel {
		UNKNOWN("UNKNOWN"), LOW("LOW"), NORMAL("NORMAL"), HIGH("HIGH");

		private String name;

		private HealthWarningLevel(String name) {
			this.name = name;
		}

		public static HealthWarningLevel getHealthWarningLevel(int heartRate) {
			if (heartRate >= 60 && heartRate <= 75) {
				return LOW;
			}
			if (heartRate > 75 && heartRate <= 120) {
				return NORMAL;
			}
			if (heartRate > 120) {
				return HIGH;
			}
			return UNKNOWN;
		}

		public String toSting() {
			return this.name;
		}
	}

	@JsonIgnore
	private static final AtomicLong count = new AtomicLong(0);

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String runningId;

	private double latitude;
	private double longitude;
	private double runningDistance;
	private double totalRunningTime;
	private int heartRate;
	private Date timestamp; // ignore input, use current time

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "id", column = @Column(name = "user_id")),
			@AttributeOverride(name = "username", column = @Column(name = "user_name")),
			@AttributeOverride(name = "address", column = @Column(name = "user_address"))
	})
	private final UserInfo userInfo;

	@JsonIgnore
	private HealthWarningLevel healthWarningLevel;

	public RunningInformation() {
		this.userInfo = null;
	}

	public RunningInformation(final String username, final String address) {
		this.userInfo = new UserInfo(username, address);
	}

	public RunningInformation(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@JsonCreator
	public RunningInformation(
			@JsonProperty("runningId") String runningId,
			@JsonProperty("latitude") String latitude,
			@JsonProperty("longitude") String longitude,
			@JsonProperty("runningDistance") String runningDistance,
			@JsonProperty("heartRate") String heartRate,
			@JsonProperty("timestamp") String timestamp,
			@JsonProperty("userInfo") UserInfo userInfo
	) {
		this.runningId = runningId;
		this.latitude = Double.parseDouble(latitude);
		this.longitude = Double.parseDouble(longitude);
		this.runningDistance = Double.parseDouble(runningDistance);
		this.heartRate = getRandomHeartRate(60, 120);
		this.healthWarningLevel = HealthWarningLevel.getHealthWarningLevel(this.heartRate);
		this.timestamp = new Date();
		this.userInfo = userInfo;
		this.userInfo.setId(count.incrementAndGet());

		System.out.println(this.heartRate);
	}

	@JsonIgnore
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	@JsonIgnore
	public double getLatitude() {
		return this.latitude;
	}

	@JsonIgnore
	public double getLongitude() {
		return this.longitude;
	}

	@JsonIgnore
	public double getRunningDistance() {
		return this.runningDistance;
	}

	@JsonIgnore
	public Date getTimestamp() {
		return this.timestamp;
	}

	public long getTotalRunningTime() {
		return (long) this.totalRunningTime;
	}

	public String getUserName() {
		return userInfo.getUsername();
	}

	public String getUserAddress() {
		return userInfo.getAddress();
	}

	public Long getUserId() {
		return userInfo.getId();
	}

	@JsonProperty("healthWarningLevel")
	public String getHealthWarningLevel() {
		return this.healthWarningLevel.toString();
	}

	// randomly generated heart rate, range: 60 ~ 200
	private int getRandomHeartRate(int min, int max) {
		Random random = new Random();
		return min + random.nextInt(max - min + 1);
	}
}
