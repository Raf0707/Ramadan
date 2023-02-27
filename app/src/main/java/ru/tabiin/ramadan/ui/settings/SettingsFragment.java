package ru.tabiin.ramadan.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.color.DynamicColors;

import ru.tabiin.ramadan.R;
import ru.tabiin.ramadan.databinding.FragmentSettingsBinding;
import ru.tabiin.ramadan.util.SharedPreferencesUtils;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container,false);
        binding.dynamicColorsSwitch.setEnabled(DynamicColors.isDynamicColorAvailable());
        binding.dynamicColorsSwitch.setChecked(SharedPreferencesUtils.getBoolean(requireContext(), "useDynamicColors"));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.appThemeRadioGroup.check(SharedPreferencesUtils.getInteger(requireContext(), "checkedButton", R.id.followSystemNightModeRadioButton));
        binding.appThemeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.followSystemNightModeRadioButton:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    SharedPreferencesUtils.saveInteger(requireContext(), "checkedButton", R.id.followSystemNightModeRadioButton);
                    SharedPreferencesUtils.saveInteger(requireContext(), "nightMode", 0);
                    requireActivity().recreate();

                    return;
                case R.id.noNightModeRadioButton:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferencesUtils.saveInteger(requireContext(), "checkedButton", R.id.noNightModeRadioButton);
                    SharedPreferencesUtils.saveInteger(requireContext(), "nightMode", 2);
                    requireActivity().recreate();
                    return;
                case R.id.nightModeRadioButton:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferencesUtils.saveInteger(requireContext(), "checkedButton", R.id.nightModeRadioButton);
                    SharedPreferencesUtils.saveInteger(requireContext(), "nightMode", 1);
                    requireActivity().recreate();
                    return;
            }
        });


        binding.dynamicColorsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DynamicColors.applyToActivitiesIfAvailable(getActivity().getApplication());
            DynamicColors.applyToActivitiesIfAvailable(getActivity().getApplication(),
                    R.style.Theme_Ramadan);
            SharedPreferencesUtils.saveBoolean(requireContext(), "useDynamicColors", isChecked);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}