package ro.digitalnation.restuflWebService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ro.digitalnation.activitati.Activitate;
import ro.digitalnation.activitati.Curs;
import ro.digitalnation.activitati.Material;
import ro.digitalnation.basic.Explorer;
import ro.digitalnation.basic.Persoana;
import ro.digitalnation.basic.Trainer;
import ro.digitalnation.structure.Track;

@RestController
public class TrackController {

	Track track = new Track();

	
	@GetMapping("/curs")
	public Curs getCurs() {
		  return track.getCurs();
	}

	@GetMapping("/exploreri")
	public List<Persoana> getExploreri() {
		  return track.getCurs().getExploreri();
	}

	@GetMapping("/trainer")
	public Persoana getTrainer() {
		return track.getCurs().getTrainer();
	}

	@GetMapping("/activitati")
	public Map<String, Activitate> getActivitati() {
		return track.getCurs().getActivitati();
	}

	@GetMapping("/activitate/{name}")
	public Activitate getActivitate(@PathVariable String name) {
		  return track.getCurs().getActivitati().get(name);
	}

	@GetMapping("/explorer/{id}")
	public Persoana getExplorerById(@PathVariable String id) {
	    for (Persoana explorer : track.getCurs().getExploreri()) {
	        System.out.println("Compare " + explorer.obtineIdentificator() + " with " + id);
	        if (explorer.obtineIdentificator().equals(id)) {
	            return explorer;
	        }
	    }
	    return null;
	}
	@PostMapping("/addExplorer")
	public ResponseEntity<String> addExplorer(@RequestBody Explorer explorer) {
	    List<Persoana> exploreri = track.getCurs().getExploreri();

	    for (Persoana existingExplorer : exploreri) {
	        if (existingExplorer.obtineIdentificator().equals(explorer.obtineIdentificator())) {
	          
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .body("Explorer with ID " + explorer.obtineIdentificator() + " already exists.");
	        }
	    }

	    exploreri.add(explorer);

	  
	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body("Explorer with ID " + explorer.obtineIdentificator() + " added successfully.");
	}
	
	@PostMapping("/populateTrack")
    public ResponseEntity<String> populateTrack(@RequestBody Map<String, Object> trackData) {
        // Trainer
        Map<String, Object> trainerData = (Map<String, Object>) trackData.get("trainer");
        Trainer trainer = createTrainer(trainerData);

        // Exploreri
        List<Map<String, Object>> explorersData = (List<Map<String, Object>>) trackData.get("exploreri");
        List<Persoana> explorers = createExplorers(explorersData);

        // Activitati
        Map<String, Map<String, Object>> activitatiData = (Map<String, Map<String, Object>>) trackData.get("activitati");

        // Curs
        String descriere = (String) trackData.get("descriere");
        String durata = (String) trackData.get("durata");
        int cost = (int) trackData.get("cost");
        Curs curs = new Curs(descriere, durata, trainer, explorers, cost);

        // Populare activitati
        for (Map.Entry<String, Map<String, Object>> entry : activitatiData.entrySet()) {
            String activitateName = entry.getKey();
            Map<String, Object> activitateData = entry.getValue();
            Activitate activitate = createActivitate(activitateData);
            curs.getActivitati().put(activitateName, activitate);
        }

        // Setare curs in track
        track.setCurs(curs);

        return ResponseEntity.status(HttpStatus.CREATED).body("Track populated successfully.");
    }

    private Trainer createTrainer(Map<String, Object> trainerData) {
        String nume = (String) trainerData.get("nume");
        String prenume = (String) trainerData.get("prenume");
        String oras = (String) trainerData.get("oras");
        Integer varsta = (Integer) trainerData.get("varsta");
        boolean casatorita = (boolean) trainerData.get("casatorita");
        String responsabilitati = (String) trainerData.get("responsabilitati");

        return new Trainer(nume, prenume, oras, varsta, casatorita);
    }

    private List<Persoana> createExplorers(List<Map<String, Object>> explorersData) {
        List<Persoana> explorers = new ArrayList<>();

        for (Map<String, Object> explorerData : explorersData) {
            String nume = (String) explorerData.get("nume");
            String prenume = (String) explorerData.get("prenume");
            String oras = (String) explorerData.get("oras");
            Integer varsta = (Integer) explorerData.get("varsta");
            boolean casatorita = (boolean) explorerData.get("casatorita");
            String responsabilitati = (String) explorerData.get("responsabilitati");

            Explorer explorer = new Explorer(nume, prenume, oras, varsta, casatorita);
            explorers.add(explorer);
        }

        return explorers;
    }

    private Activitate createActivitate(Map<String, Object> activitateData) {
        String descriere = (String) activitateData.get("descriere");
        String durata = (String) activitateData.get("durata");

        // Puteți adăuga condiții aici pentru a identifica tipul de activitate și crea obiectul potrivit
        // Momentan, se presupune că există doar activitățile Material, Rush și Tema
        return new Material();  // Schimbați la nevoie
    }
}
	

