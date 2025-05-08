package integration4.evalebike.controller.viewModel;


import integration4.evalebike.domain.VisualInspection;

public record VisualInspectionViewModel(
        String tires,
        String bell,
        String cranks,
        String electricWiring,
        String frontFork,
        String handles,
        String chainBelt,
        String pedals,
        String reflectors,
        String brakePads,
        String brakeHandles,
        String brakeCables,
        String brakeDiscs,
        String mudguards,
        String handleBar,
        String rearSprocket,
        String frontSprocket,
        String rimsSpokes,
        String rearSuspension,
        String suspensionFront,
        String saddle
) {

    public static VisualInspectionViewModel toViewModel(VisualInspection record) {
        if (record == null) {
            return null;
        }
        return new VisualInspectionViewModel(
                record.getTires(),
                record.getBell(),
                record.getCranks(),
                record.getElectricWiring(),
                record.getFrontFork(),
                record.getHandles(),
                record.getChainBelt(),
                record.getPedals(),
                record.getReflectors(),
                record.getBrakePads(),
                record.getBrakeHandles(),
                record.getBrakeCables(),
                record.getBrakeDiscs(),
                record.getMudguards(),
                record.getHandleBar(),
                record.getRearSprocket(),
                record.getFrontSprocket(),
                record.getRimsSpokes(),
                record.getRearSuspension(),
                record.getSuspensionFront(),
                record.getSaddle()
        );
    }
}
